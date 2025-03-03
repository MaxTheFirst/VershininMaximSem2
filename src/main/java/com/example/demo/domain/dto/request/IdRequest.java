package com.example.demo.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IdRequest {

  @NotBlank(message = "ID cannot be empty")
  private Long id;
}
