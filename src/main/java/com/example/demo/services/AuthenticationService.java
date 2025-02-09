package com.example.demo.services;

import com.example.demo.controllers.requests.SignRequest;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public String signUp(SignRequest request) {

    var user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

    userService.create(user);

    return jwtService.generateToken(user);
  }

  public String signIn(SignRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    ));

    var user = userService
        .userDetailsService()
        .loadUserByUsername(request.getUsername());

    return jwtService.generateToken(user);
  }
}