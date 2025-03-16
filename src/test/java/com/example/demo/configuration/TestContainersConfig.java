package com.example.demo.configuration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainersConfig {
  private static final CassandraContainer<?> cassandraContainer = new CassandraContainer<>(DockerImageName.parse("cassandra:4.0"))
      .withExposedPorts(9042);

  static {
    cassandraContainer.start();
  }

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.cassandra.contact-points", cassandraContainer::getContainerIpAddress);
    registry.add("spring.data.cassandra.port", () -> cassandraContainer.getMappedPort(9042));
  }
}
