package com.example.demo.service;

import com.example.demo.domain.model.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

  private final UserRepository repository;

  public User save(User user) {
    return repository.save(user);
  }

  public User create(User user) {
    if (repository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistsException("User with this username is already exists");
    }

    return save(user);
  }

  public User getByUsername(String username) {
    Optional<User> user = repository.findByUsername(username);
    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username not found");
    }
    return user.get();
  }

  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  public User getCurrentUser() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);
  }
}
