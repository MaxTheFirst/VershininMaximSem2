package com.example.demo.service;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebsitesService {

  private final WebsiteRepository repository;
  private final UsersService usersService;

  public List<Website> getDefaultWebsites() {
    return repository.getDefaultWebsite();
  }

  public List<Website> getUserWebsites() {
    User user = usersService.getCurrentUser();
    return repository.getUserWebsite(user);
  }

  public void chooseWebsite(Long websiteId) {
    Optional<Website> website = repository.findById(websiteId);
    if (website.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Website with this ID not found");
    }
    User user = usersService.getCurrentUser();
    repository.chooseWebsite(user, website.get());
  }

  public void removeWebsite(Long websiteId) {
    Optional<Website> website = repository.findById(websiteId);
    if (website.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Website with this ID not found");
    }
    User user = usersService.getCurrentUser();
    repository.removeWebsite(user, website.get());
  }

  public Website createWebsite(Website website) {
    User user = usersService.getCurrentUser();
    website.setOwner(user);
    return repository.createWebsite(website);
  }
}
