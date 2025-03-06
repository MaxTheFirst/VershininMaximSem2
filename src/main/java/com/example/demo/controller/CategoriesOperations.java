package com.example.demo.controller;


import com.example.demo.domain.dto.response.CategoryResponse;
import com.example.demo.domain.model.Category;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Categories operations", description = "Управление категориями")
@RequestMapping("/api/categories")
@RateLimiter(name = "apiRateLimiter")
public interface CategoriesOperations {

  @Operation(summary = "Получить стандартные категории")
  @ApiResponse(responseCode = "200", description = "Список стандартных категорий")
  @GetMapping
  ResponseEntity<List<CategoryResponse>> getDefaultCategories();

  @Operation(summary = "Получить пользовательские категории")
  @ApiResponse(responseCode = "200", description = "Список пользовательских категорий")
  @GetMapping("/my")
  ResponseEntity<List<CategoryResponse>> getUserCategories();

  @Operation(summary = "Создать категорию")
  @ApiResponse(responseCode = "201", description = "Категория создана")
  @PostMapping
  ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid Category category);

  @Operation(summary = "Выбрать категорию")
  @ApiResponse(responseCode = "200", description = "Категория выбрана")
  @PutMapping("/{categoryId}")
  ResponseEntity<Void> chooseCategory(@PathVariable Long categoryId);

  @Operation(summary = "Отмена выбора категории")
  @ApiResponse(responseCode = "200", description = "Выбор отменен")
  @DeleteMapping("/{categoryId}")
  ResponseEntity<Void> removeCategory(@PathVariable Long categoryId);

  @Operation(summary = "Редактирование процента категории")
  @ApiResponse(responseCode = "200", description = "Изменение сохранено")
  @PatchMapping("/{categoryId}")
  ResponseEntity<Void> editCategoryPercent(@PathVariable Long categoryId);
}

