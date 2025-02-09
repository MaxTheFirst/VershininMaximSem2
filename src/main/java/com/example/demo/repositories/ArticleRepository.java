package com.example.demo.repositories;

import com.example.demo.models.Article;
import com.example.demo.models.User;

import java.util.Set;

public interface ArticleRepository {
  void addArticle(Article article);
  Set<Article> getNewArticleForUser(User user);
  void deleteArticles();
}
