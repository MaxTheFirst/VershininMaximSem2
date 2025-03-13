package com.example.demo.controller.impl;

import com.example.demo.controller.ArticlesOperations;
import com.example.demo.domain.model.Article;
import com.example.demo.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticlesController implements ArticlesOperations {

  private final ArticlesService service;

  @Override
  public ResponseEntity<List<Article>> getNewArticles() {
    return ResponseEntity.ok().body(service.getNewArticles());
  }
}
