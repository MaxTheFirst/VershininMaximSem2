package com.example.demo.controller.impl;

import com.example.demo.controller.WebsitesOperations;
import com.example.demo.domain.dto.response.WebsiteResponse;
import com.example.demo.domain.model.Website;
import com.example.demo.service.WebsitesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebsitesController implements WebsitesOperations {

  private final WebsitesService service;

  @Override
  public ResponseEntity<List<WebsiteResponse>> getDefaultWebsites() {
    return ResponseEntity.ok(service.getDefaultWebsites());
  }

  @Override
  public ResponseEntity<List<WebsiteResponse>> getUserWebsites() {
    return ResponseEntity.ok(service.getUserWebsites());
  }

  @Override
  public ResponseEntity<WebsiteResponse> createWebsite(@Valid Website website) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createWebsite(website));
  }

  @Override
  public ResponseEntity<Void> chooseWebsite(Long websiteId) {
    service.chooseWebsite(websiteId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> removeWebsite(Long websiteId) {
    service.removeWebsite(websiteId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> editWebsitePercent(Long websiteId) {
    //todo
    return ResponseEntity.ok().build();
  }
}