package com.example.demo.aop.audit;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserActionDTO {
    private Long userId;
    private Instant eventTime;
    private Action eventType;
    private String eventDetails;
}
