package com.example.demo.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Ответ с токеном аутентификации")
public class JwtAuthenticationResponse {

  @Schema(description = "Токен доступа (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;

  @Schema(description = "Тип токена", example = "Bearer")
  private final String tokenType = "Bearer";
}
