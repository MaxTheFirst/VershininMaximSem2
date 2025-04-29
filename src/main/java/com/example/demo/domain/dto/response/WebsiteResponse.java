package com.example.demo.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Вебсайт (для ответа пользователю)")
public class WebsiteResponse {

  @Schema(description = "Идентификатор вебсайта", example = "1")
  private Long id;

  @Schema(description = "Название вебсайта", example = "Google")
  private String name;

  @Schema(description = "URL вебсайта", example = "https://www.google.com")
  private String url;
}