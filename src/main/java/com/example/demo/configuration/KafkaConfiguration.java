package com.example.demo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic getTopic(@Value("${topic-to-send-message}") String topic){
    return new NewTopic(topic, 1, (short) 1);
  }
}
