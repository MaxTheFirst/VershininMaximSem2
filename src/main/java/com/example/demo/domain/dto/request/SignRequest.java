package com.example.demo.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию/авторизацию")
public class SignRequest {

  @NotBlank(message = "Имя пользователя не может быть пустым")
  @Schema(description = "Имя пользователя (без пробелов и спецсимволов)", example = "user_123")
  private String username;

  @NotBlank(message = "Пароль не может быть пустым")
  @Schema(description = "Пароль (не менее 8 символов, минимум 1 цифра, 1 заглавная буква и 1 спецсимвол)",
      example = "P@ssw0rd123")
  private String password;
}
