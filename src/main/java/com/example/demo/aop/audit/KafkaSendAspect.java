package com.example.demo.aop.audit;

import com.example.demo.domain.model.User;
import com.example.demo.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class KafkaSendAspect {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final NewTopic topic;
  private final ObjectMapper objectMapper;

  private final UsersService usersService;

  @AfterReturning(pointcut = "@annotation(sendAudit)", returning = "result")
  public void afterMethodExecution(JoinPoint joinPoint, SendAudit sendAudit, Object result) throws Throwable {
    Object[] args = joinPoint.getArgs();

    Map<String, Object> details = new HashMap<>();
    details.put("args", args);
    details.put("result", result);

    String detailsJson = objectMapper.writeValueAsString(details);

    User user = usersService.getCurrentUser();
    Instant instant = Instant.now();

    UserActionDTO userAction = UserActionDTO.builder()
        .userId(user.getId())
        .eventTime(instant)
        .eventType(sendAudit.action())
        .eventDetails(detailsJson).build();

    String jsonMessage = objectMapper.writeValueAsString(userAction);

    kafkaTemplate.send(topic.name(), jsonMessage);
  }
}
