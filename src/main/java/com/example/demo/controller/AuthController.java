package com.example.demo.controller;

import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService service;

  @PostMapping("/sign-up")
  public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.signUp(request));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignRequest request) {
    return ResponseEntity.ok(service.signIn(request));
  }
}