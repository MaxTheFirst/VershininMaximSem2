package com.example.demo.domain.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос, содержащий идентификатор")
public class IdRequest {

  @NotBlank(message = "ID не может быть пустым")
  @Schema(description = "Уникальный идентификатор", example = "123")
  private Long id;
}