package com.example.demo.service;

import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import com.example.demo.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
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

    @Async
  public List<Article> getNewArticles() {
    User user = usersService.getCurrentUser();
        CompletableFuture<List<Article>> future = new CompletableFuture<>();
        future.complete(repository.getNewArticlesForUser(user));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
  }
}
