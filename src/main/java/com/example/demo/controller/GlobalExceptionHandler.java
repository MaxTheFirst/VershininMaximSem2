package com.example.demo.controller;

import com.example.demo.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException e) {
    return buildErrorResponse(e.getStatusCode().value(), e.getReason());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage()))
        .toList();

    return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(int status, String message) {
    return buildErrorResponse(status, message, null);
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(int status, String message, List<Map<String, String>> errors) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", status);
    response.put("message", message);
    if (errors != null) {
      response.put("errors", errors);
    }
    return ResponseEntity.status(status).body(response);
  }
}
