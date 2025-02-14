package com.example.demo.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignRequest {

  @NotBlank(message = "Username cannot be empty")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must not contain spaces or special characters")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$", message = "Password must consist of more than 8 characters and contain at least one digit and at least one special character.")
  private String password;
}