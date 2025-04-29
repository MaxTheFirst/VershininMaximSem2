package com.example.demo.controller.impl;

import com.example.demo.controller.AuthOperations;
import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthOperations {

  private final AuthenticationService service;

  @Override
  public ResponseEntity<JwtAuthenticationResponse> signUp(@Valid SignRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.signUp(request));
  }

  @Override
  public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid SignRequest request) {
    return ResponseEntity.ok(service.signIn(request));
  }
}