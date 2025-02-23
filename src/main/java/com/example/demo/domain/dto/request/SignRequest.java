package com.example.demo.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию/авторизацию")
public class SignRequest {

  @NotBlank(message = "Имя пользователя не может быть пустым")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$",
      message = "Имя пользователя не должно содержать пробелы или специальные символы")
  @Schema(description = "Имя пользователя (без пробелов и спецсимволов)", example = "user_123")
  private String username;

  @NotBlank(message = "Пароль не может быть пустым")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
      message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, одну заглавную букву и один специальный символ")
  @Schema(description = "Пароль (не менее 8 символов, минимум 1 цифра, 1 заглавная буква и 1 спецсимвол)",
      example = "P@ssw0rd123")
  private String password;
}
