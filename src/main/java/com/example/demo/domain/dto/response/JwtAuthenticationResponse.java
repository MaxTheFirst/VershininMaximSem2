package com.example.demo.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class JwtAuthenticationResponse {

  private String accessToken;
  private final String tokenType = "Bearer";
}
