package com.example.demo.controller.impl;

import com.example.demo.controller.WebsitesOperations;
import com.example.demo.domain.dto.request.IdRequest;
import com.example.demo.domain.model.Website;
import com.example.demo.service.WebsitesService;
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
@RequiredArgsConstructor
public class WebsitesController implements WebsitesOperations {

  private final WebsitesService service;

  @Override
  public ResponseEntity<List<Website>> getDefaultWebsites() {
    return ResponseEntity.ok(service.getDefaultWebsites());
  }

  @Override
  public ResponseEntity<List<Website>> getUserWebsites() {
    return ResponseEntity.ok(service.getUserWebsites());
  }

  @Override
  public ResponseEntity<Website> createWebsite(@Valid Website website) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createWebsite(website));
  }

  @Override
  public ResponseEntity<Void> chooseWebsite(@Valid IdRequest idRequest) {
    service.chooseWebsite(idRequest.getId());
    return ResponseEntity.ok().build();
  }
}