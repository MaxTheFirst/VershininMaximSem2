package com.example.demo.controller;

import com.example.demo.domain.dto.request.IdRequest;
import com.example.demo.domain.model.Category;
import com.example.demo.service.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

  private final CategoriesService service;

  @GetMapping
  public ResponseEntity<List<Category>> getDefaultCategories() {
    return ResponseEntity.ok(service.getDefaultCategories());
  }

  @GetMapping("/my")
  public ResponseEntity<List<Category>> getUserCategories() {
    return ResponseEntity.ok(service.getUserCategories());
  }

  @PostMapping()
  public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(category));
  }

  @PutMapping
  public ResponseEntity<Void> chooseCategory(@RequestBody @Valid IdRequest idRequest) {
    service.chooseCategory(idRequest.getId());
    return ResponseEntity.ok().build();
  }
}