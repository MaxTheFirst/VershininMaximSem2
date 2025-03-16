package com.example.demo.service;

import com.example.demo.configuration.TestContainersConfig;
import com.example.demo.domain.dto.UserActionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestContainersConfig.class)
class UserAuditServiceTest {

  @Autowired
  private UserAuditService userAuditService;

  @Test
  void successInsertUserAction() {
    Long userId = 123L;
    Instant eventTime = Instant.now();
    String eventDetails = "User logged in";

    userAuditService.insertUserAction(userId, eventTime, UserAuditService.Action.INSERT, eventDetails);

    Optional<UserActionDTO> result = userAuditService.selectUserAction(userId, eventTime);

    assertThat(result).isPresent();
    assertThat(result.get().getUserId()).isEqualTo(userId);
    assertThat(result.get().getEventType()).isEqualTo(UserAuditService.Action.INSERT.toString());
    assertThat(result.get().getEventDetails()).isEqualTo(eventDetails);
  }

  @Test
  void selectUserAction() {
  }
}