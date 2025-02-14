package com.example.demo.controller;

import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService authenticationService;

  @PostMapping("/sign-up")
  public JwtAuthenticationResponse signUp(@RequestBody @Valid SignRequest request) {
    return new JwtAuthenticationResponse(authenticationService.signUp(request));
  }

  @PostMapping("/sign-in")
  public JwtAuthenticationResponse signIn(@RequestBody @Valid SignRequest request) {
    return new JwtAuthenticationResponse(authenticationService.signIn(request));
  }
}