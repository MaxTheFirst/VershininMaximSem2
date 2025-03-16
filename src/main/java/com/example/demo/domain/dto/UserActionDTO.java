package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
public class UserActionDTO {
  private Long userId;
  private Instant eventTime;
  private String eventType;
  private String eventDetails;
}
