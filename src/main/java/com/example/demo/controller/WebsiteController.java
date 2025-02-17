package com.example.demo.controller;

import com.example.demo.domain.model.Website;
import com.example.demo.service.WebsiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/websites")
@RequiredArgsConstructor
public class WebsiteController {

  private final WebsiteService service;

  @GetMapping("/default")
  public ResponseEntity<List<Website>> getDefaultWebsites() {
    return ResponseEntity.ok(service.getDefaultWebsites());
  }

  @GetMapping("/my")
  public ResponseEntity<List<Website>> getUserWebsites() {
    return ResponseEntity.ok(service.getUserWebsites());
  }

  @PostMapping("/add")
  public ResponseEntity<Website> createWebsite(@RequestBody Website website) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createWebsite(website));
  }

  @PostMapping("/choose/{id}")
  public ResponseEntity<Void> chooseWebsite(@PathVariable Long id) {
    service.chooseWebsite(id);
    return ResponseEntity.ok().build();
  }
}
