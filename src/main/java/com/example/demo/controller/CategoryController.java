package com.example.demo.controller;

import com.example.demo.domain.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService service;

  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    return ResponseEntity.ok(service.getAllCategory());
  }

  @GetMapping("/default")
  public ResponseEntity<List<Category>> getDefaultCategories() {
    return ResponseEntity.ok(service.getDefaultCategories());
  }

  @GetMapping("/my")
  public ResponseEntity<List<Category>> getUserCategories() {
    return ResponseEntity.ok(service.getUserCategories());
  }

  @PostMapping("/add")
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(category));
  }

  @PostMapping("/choose/{id}")
  public ResponseEntity<Void> chooseCategory(@PathVariable Long id) {
    service.chooseCategory(id);
    return ResponseEntity.ok().build();
  }
}