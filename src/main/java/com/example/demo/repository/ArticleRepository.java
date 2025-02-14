package com.example.demo.repository;

import com.example.demo.domain.dto.model.Article;
import com.example.demo.domain.dto.model.User;

import java.util.Set;

public interface ArticleRepository {
  void addArticle(Article article);
  Set<Article> getNewArticlesForUser(User user);
}
