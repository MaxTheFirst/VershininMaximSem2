package com.example.demo.service;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.repository.WebsiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebsiteService {

  private final WebsiteRepository repository;
  private final UserService userService;

  public List<Website> getDefaultWebsites() {
    return repository.getDefaultWebsite();
  }

  public List<Website> getUserWebsites() {
    User user = userService.getCurrentUser();
    return repository.getUserWebsite(user);
  }

  public void chooseWebsite(Long websiteId) {
    Optional<Website> website = repository.findById(websiteId);
    if (website.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Website with this ID not found");
    }
    User user = userService.getCurrentUser();
    repository.chooseCategory(user, website.get());
  }

  public Website createWebsite(Website website) {
    User user = userService.getCurrentUser();
    website.setOwner(user);
    return repository.createWebsite(website);
  }
}
