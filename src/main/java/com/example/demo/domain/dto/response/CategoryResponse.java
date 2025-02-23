package com.example.demo.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Schema(description = "Категория (для ответа пользователю)")
public class CategoryResponse {

  @Schema(description = "Идентификатор категории", example = "1")
  private Long id;

  @Schema(description = "Название категории", example = "Технологии")
  private String name;
}
