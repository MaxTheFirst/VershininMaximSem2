package com.example.demo.service;

import com.example.demo.aop.audit.Action;
import com.example.demo.aop.audit.SendAudit;
import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import com.example.demo.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ArticlesService {

  private final ArticlesRepository repository;
  private final UsersService usersService;

  @SendAudit(action = Action.SELECT)
  public List<Article> getNewArticles() {
    User user = usersService.getCurrentUser();
    return repository.getNewArticlesForUser(user);
  }
}
