package com.example.demo.controller;

import com.example.demo.domain.model.Article;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Articles operations", description = "Управление статьями")
@RequestMapping("/api/articles")
public interface ArticlesOperations {

  @Operation(summary = "Получить новые статьи")
  @ApiResponse(responseCode = "200", description = "Список новых статей")
  @GetMapping
  ResponseEntity<List<Article>> getNewArticles();
}
