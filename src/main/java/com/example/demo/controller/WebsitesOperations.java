package com.example.demo.controller;

import com.example.demo.domain.dto.response.WebsiteResponse;
import com.example.demo.domain.model.Website;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

@Tag(name = "Websites operations", description = "Управление веб-сайтами")
@RequestMapping("/api/websites")
@CircuitBreaker(name = "apiCircuitBreaker")
@RateLimiter(name = "apiRateLimiter")
public interface WebsitesOperations {

  @Operation(summary = "Получить стандартные веб-сайты")
  @ApiResponse(responseCode = "200", description = "Список стандартных сайтов")
  @GetMapping
  ResponseEntity<List<WebsiteResponse>> getDefaultWebsites();

  @Operation(summary = "Получить пользовательские веб-сайты")
  @ApiResponse(responseCode = "200", description = "Список сайтов пользователя")
  @GetMapping("/my")
  ResponseEntity<List<WebsiteResponse>> getUserWebsites();

  @Operation(summary = "Создать веб-сайт")
  @ApiResponse(responseCode = "201", description = "Веб-сайт создан")
  @PostMapping
  ResponseEntity<WebsiteResponse> createWebsite(@RequestBody @Valid Website website);

  @Operation(summary = "Выбрать веб-сайт")
  @ApiResponse(responseCode = "200", description = "Веб-сайт выбран")
  @PutMapping("/{websiteId}")
  ResponseEntity<Void> chooseWebsite(@PathVariable Long websiteId);

  @Operation(summary = "Отмена выбора веб-сайта")
  @ApiResponse(responseCode = "200", description = "Выбор отменен")
  @DeleteMapping("/{websiteId}")
  ResponseEntity<Void> removeWebsite(@PathVariable Long websiteId);

  @Operation(summary = "Редактирование процента веб-сайта")
  @ApiResponse(responseCode = "200", description = "Изменение сохранено")
  @PatchMapping("/{websiteId}")
  ResponseEntity<Void> editWebsitePercent(@PathVariable Long websiteId);
}
