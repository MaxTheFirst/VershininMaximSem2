package com.example.demo.controller.impl;

import com.example.demo.controller.CategoriesOperations;
import com.example.demo.domain.dto.request.IdRequest;
import com.example.demo.domain.model.Category;
import com.example.demo.service.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoriesController implements CategoriesOperations {

  private final CategoriesService service;

  @Override
  public ResponseEntity<List<Category>> getDefaultCategories() {
    return ResponseEntity.ok(service.getDefaultCategories());
  }

  @Override
  public ResponseEntity<List<Category>> getUserCategories() {
    return ResponseEntity.ok(service.getUserCategories());
  }

  @Override
  public ResponseEntity<Category> createCategory(@Valid Category category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(category));
  }

  @Override
  public ResponseEntity<Void> chooseCategory(Long categoryId) {
    service.chooseCategory(categoryId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> removeCategory(Long categoryId) {
    service.removeCategory(categoryId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> editCategoryPercent(Long categoryId) {
    //todo
    return ResponseEntity.ok().build();
  }
}