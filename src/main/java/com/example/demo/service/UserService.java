package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.domain.dto.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private UserRepository repository;

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
      throw new UsernameNotFoundException("User with this username not found");
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
