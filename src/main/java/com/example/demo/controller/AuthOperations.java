package com.example.demo.controller;
import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication operations", description = "Авторизация и регистрация")
@RequestMapping("/api/auth")
public interface AuthOperations {

  @Operation(summary = "Регистрация пользователя")
  @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован")
  @PostMapping("/sign-up")
  ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignRequest request);

  @Operation(summary = "Авторизация пользователя")
  @ApiResponse(responseCode = "200", description = "Успешный вход в систему")
  @PostMapping("/sign-in")
  ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignRequest request);
}
