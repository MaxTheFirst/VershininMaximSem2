package com.example.demo.controller;

import com.example.demo.domain.dto.request.IdRequest;
import com.example.demo.domain.model.Website;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Websites API", description = "Управление веб-сайтами")
@RequestMapping("/api/websites")
public interface WebsitesOperations {

  @Operation(summary = "Получить стандартные веб-сайты")
  @ApiResponse(responseCode = "200", description = "Список стандартных сайтов")
  @GetMapping
  ResponseEntity<List<Website>> getDefaultWebsites();

  @Operation(summary = "Получить пользовательские веб-сайты")
  @ApiResponse(responseCode = "200", description = "Список сайтов пользователя")
  @GetMapping("/my")
  ResponseEntity<List<Website>> getUserWebsites();

  @Operation(summary = "Создать веб-сайт")
  @ApiResponse(responseCode = "201", description = "Веб-сайт создан")
  @PostMapping
  ResponseEntity<Website> createWebsite(@RequestBody @Valid Website website);

  @Operation(summary = "Выбрать веб-сайт")
  @ApiResponse(responseCode = "200", description = "Веб-сайт выбран")
  @PutMapping
  ResponseEntity<Void> chooseWebsite(@RequestBody @Valid IdRequest idRequest);
}
