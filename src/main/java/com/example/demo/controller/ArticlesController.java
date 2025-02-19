package com.example.demo.controller;

import com.example.demo.domain.model.Article;
import com.example.demo.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticlesController {

  private final ArticlesService service;

  @GetMapping
  public ResponseEntity<List<Article>> getNewArticles() {
    return ResponseEntity.ok().body(service.getNewArticles());
  }
}
