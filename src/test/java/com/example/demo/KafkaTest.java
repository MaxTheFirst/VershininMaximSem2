package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.aop.audit.Action;
import com.example.demo.aop.audit.UserActionDTO;
import com.example.demo.configuration.KafkaTestConsumer;
import com.example.demo.configuration.ObjectMapperTestConfig;
import com.example.demo.configuration.TestContainersPostgresConfig;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.service.UsersService;
import com.example.demo.service.WebsitesService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({TestContainersPostgresConfig.class, KafkaAutoConfiguration.class, ObjectMapperTestConfig.class})
@Testcontainers
@ActiveProfiles("test")
public class KafkaTest {

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @Autowired
  private UsersService usersService;

  @Autowired
  private WebsitesService websitesService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private NewTopic testTopic;

  private User testUser;

  @BeforeAll
  void setUp() {
    testUser = usersService.create(User.builder()
        .username("user_123456")
        .password("P@ssw0rd123456")
        .build());
  }

  @Test
  public void shouldSendKafkaSuccessfully() {
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(testUser.getUsername(), null, List.of())
    );

    assertDoesNotThrow(() -> websitesService.chooseWebsite(1L));

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "group-id-" + UUID.randomUUID());
    consumer.subscribe(List.of(testTopic.name()));
    ConsumerRecords<String, String> records = consumer.poll();

    assertEquals(1, records.count(), "Ожидалось одно сообщение в Kafka");

    records.forEach(record -> {
      try {
        UserActionDTO dto = objectMapper.readValue(record.value(), UserActionDTO.class);
        assertEquals(testUser.getId(), dto.getUserId());
        assertEquals(Action.SELECT, dto.getEventType());
        assertTrue(dto.getEventDetails().contains("args"));
      } catch (JsonProcessingException e) {
        fail("Не удалось распарсить сообщение из Kafka: " + e.getMessage());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Test
  void shouldNotSendKafka() {
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(testUser.getUsername(), null, List.of())
    );

    assertThrows(ResponseStatusException.class, () -> websitesService.chooseWebsite(100L));

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "group-id-" + UUID.randomUUID());
    consumer.subscribe(List.of(testTopic.name()));
    ConsumerRecords<String, String> records = consumer.poll();

    assertEquals(0, records.count(), "В Kafka нет сообщений");
  }
}
