package com.example.demo.service;

import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import com.example.demo.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticlesService {

  private final ArticlesRepository repository;
  private final UsersService usersService;

  public List<Article> getNewArticles() {
    User user = usersService.getCurrentUser();
    return repository.getNewArticlesForUser(user);
  }
}
