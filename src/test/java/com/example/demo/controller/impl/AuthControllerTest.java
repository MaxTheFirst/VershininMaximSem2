package com.example.demo.controller.impl;


import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AuthenticationService authenticationService;
  @MockitoBean
  private JwtService jwtService;
  @MockitoBean
  private UsersService usersService;

  private static final String USER_SIGN_UP = "{\"username\":\"user_123\", \"password\":\"P@ssw0rd123\"}";
  private static final String USER_SIGN_IN = "{\"username\":\"user_123\", \"password\":\"P@ssw0rd123\"}";
  private final JwtAuthenticationResponse testResponse = new JwtAuthenticationResponse("sampleToken");

  @Test
  @WithMockUser
  public void successSignUp() throws Exception {
    when(authenticationService.signUp(any(SignRequest.class))).thenReturn(testResponse);
    ResultActions resultActions = mockMvc.perform(post("/api/auth/sign-up")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                    .contentType("application/json")
                    .content(USER_SIGN_UP))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.accessToken").value(testResponse.getAccessToken()))
            .andExpect(jsonPath("$.tokenType").value(testResponse.getTokenType()));
  }

  @Test
  @WithMockUser
  public void failSignUp() throws Exception {
    when(authenticationService.signUp(any(SignRequest.class))).thenThrow(new UserAlreadyExistsException("User with this username is already exists"));
    mockMvc.perform(post("/api/auth/sign-up")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType("application/json")
            .content(USER_SIGN_UP))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  public void successSignIn() throws Exception {
    when(authenticationService.signIn(any(SignRequest.class))).thenReturn(testResponse);
    mockMvc.perform(post("/api/auth/sign-in")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType("application/json")
            .content(USER_SIGN_IN))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value(testResponse.getAccessToken()))
        .andExpect(jsonPath("$.tokenType").value(testResponse.getTokenType()));
  }

  @Test
  @WithMockUser
  public void failSignIn() throws Exception {
    when(authenticationService.signIn(any(SignRequest.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username not found"));
    mockMvc.perform(post("/api/auth/sign-in")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType("application/json")
            .content(USER_SIGN_IN))
        .andExpect(status().isNotFound());
  }
}