package com.example.demo.service;

import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import com.example.demo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository repository;
  private final UserService userService;

  public List<Article> getNewArticles() {
    User user = userService.getCurrentUser();
    return repository.getNewArticlesForUser(user);
  }
}
