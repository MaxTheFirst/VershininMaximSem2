package com.example.demo;

import com.example.demo.configuration.TestContainersConfig;
import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.CategoryResponse;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.domain.dto.response.WebsiteResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConfiguration
@Import(TestContainersConfig.class)
@ActiveProfiles("test")
public class EndToEndTest {

    private static final String LOCAL_URL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    @BeforeAll
    void setUp() {
        SignRequest signUpRequest = new SignRequest("user_123", "P@ssw0rd123");
        ResponseEntity<JwtAuthenticationResponse> signUpResponse = restTemplate.postForEntity(getAllUrl("/api/auth/sign-up"), signUpRequest, JwtAuthenticationResponse.class);
        accessToken = signUpResponse.getBody().getAccessToken();
    }

    @Test
    public void fullUserFlowTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<CategoryResponse[]> categoriesResponse = restTemplate.exchange(
                getAllUrl("/api/categories"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CategoryResponse[].class
        );
        assertEquals(200, categoriesResponse.getStatusCode().value());

        CategoryResponse chosenCategory = categoriesResponse.getBody()[0];

        restTemplate.exchange(
                getAllUrl("/api/categories/" + chosenCategory.getId()),
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                Void.class
        );

        ResponseEntity<WebsiteResponse[]> websitesResponse = restTemplate.exchange(
                getAllUrl("/api/websites"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                WebsiteResponse[].class
        );
        assertEquals(200, websitesResponse.getStatusCode().value());
        WebsiteResponse chosenWebsite = websitesResponse.getBody()[0];

        restTemplate.exchange(
                getAllUrl("/api/websites/" + chosenWebsite.getId()),
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                Void.class
        );

        ResponseEntity<CategoryResponse[]> userCategoriesResponse = restTemplate.exchange(
                getAllUrl("/api/categories/my"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                CategoryResponse[].class
        );
        assertEquals(200, userCategoriesResponse.getStatusCode().value());
        assertTrue(Arrays.asList(userCategoriesResponse.getBody()).contains(chosenCategory));

        ResponseEntity<WebsiteResponse[]> userWebsitesResponse = restTemplate.exchange(
                getAllUrl("/api/websites/my"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                WebsiteResponse[].class
        );
        assertEquals(200, userWebsitesResponse.getStatusCode().value());
        assertTrue(Arrays.asList(userWebsitesResponse.getBody()).contains(chosenWebsite));
    }

    private String getAllUrl(String path) {
        return LOCAL_URL + port + path;
    }
}
