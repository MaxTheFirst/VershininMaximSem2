package com.example.demo.repository.impl;

import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import com.example.demo.repository.ArticleRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

  private final EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public List<Article> getNewArticlesForUser(User user) {
    List<Article> newArticles = new ArrayList<>();

    String queryStr = "SELECT uws.lastSentArticleDate, a " +
        "FROM UserWebsiteStatus uws " +
        "JOIN uws.website w " +
        "JOIN a.category c " +
        "JOIN a.website ws " +
        "WHERE uws.user = :user " +
        "AND (uws.lastSentArticleDate IS NULL OR a.date > uws.lastSentArticleDate) " +
        "AND (c IN :categories AND ws IN :websites)";

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
