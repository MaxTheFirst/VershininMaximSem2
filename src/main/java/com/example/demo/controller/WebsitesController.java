package com.example.demo.controller;

import com.example.demo.domain.model.Website;
import com.example.demo.service.WebsitesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/websites")
@RequiredArgsConstructor
public class WebsitesController {

  private final WebsitesService service;

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
