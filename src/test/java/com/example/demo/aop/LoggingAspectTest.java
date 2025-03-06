package com.example.demo.aop;

import com.example.demo.configuration.TestContainersConfig;
import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConfiguration
@Import(TestContainersConfig.class)
@ActiveProfiles("test")
class LoggingAspectTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAspect() {
        int startValue = LoggingAspect.count;
        SignRequest signUpRequest = new SignRequest("user_12345", "P@ssw0rd123");
        ResponseEntity<JwtAuthenticationResponse> signUpResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/auth/sign-up", signUpRequest, JwtAuthenticationResponse.class);

        assertEquals(LoggingAspect.count, startValue + 2);
    }
}