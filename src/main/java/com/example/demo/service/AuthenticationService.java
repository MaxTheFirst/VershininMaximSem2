package com.example.demo.service;

import com.example.demo.domain.dto.request.SignRequest;
import com.example.demo.domain.dto.response.JwtAuthenticationResponse;
import com.example.demo.domain.model.User;
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

  public JwtAuthenticationResponse signUp(SignRequest request) {

    var user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

    userService.create(user);

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  public JwtAuthenticationResponse signIn(SignRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    ));

    var user = userService
        .userDetailsService()
        .loadUserByUsername(request.getUsername());

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }
}