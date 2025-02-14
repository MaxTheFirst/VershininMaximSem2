package com.example.demo.exception;

public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String message) {
    super(message);
  }
}
