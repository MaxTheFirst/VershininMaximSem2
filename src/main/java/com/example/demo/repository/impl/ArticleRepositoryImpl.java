package com.example.demo.repository;

import com.example.demo.domain.dto.model.Article;
import com.example.demo.domain.dto.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

  private final EntityManager entityManager;

  @Override
  public void addArticle(Article article) {
    entityManager.persist(article);
  }

  @Override
  public Set<Article> getNewArticlesForUser(User user) {
    Set<Article> newArticles = new HashSet<>();
    
    String queryStr = "SELECT uws.lastSentArticleDate, a " +
        "FROM UserWebsiteStatus uws " +
        "JOIN uws.website w " +
        "JOIN a.category c " +
        "JOIN a.website ws " +
        "WHERE uws.user = :user " +
        "AND (uws.lastSentArticleDate IS NULL OR a.date > uws.lastSentArticleDate) " +
        "AND (c IN :categories OR ws IN :websites)";

    Query query = entityManager.createQuery(queryStr, Object[].class);
    query.setParameter("user", user);
    query.setParameter("categories", user.getCategories());
    query.setParameter("websites", user.getWebsites());
    
    var results = query.getResultList();
    for (Object result : results) {
      Article article = (Article) result;
      newArticles.add(article);
    }

    return newArticles;
  }
}
