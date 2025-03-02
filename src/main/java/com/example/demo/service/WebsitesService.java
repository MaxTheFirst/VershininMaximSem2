package com.example.demo.service;

import com.example.demo.domain.dto.response.WebsiteResponse;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebsitesService {

  private final WebsiteRepository repository;
  private final UsersService usersService;

  public List<WebsiteResponse> getDefaultWebsites() {
    List<Website> websites = repository.getDefaultWebsites();
    return websites.stream()
        .map(website -> new WebsiteResponse(website.getId(), website.getName(), website.getUrl()))
        .collect(Collectors.toList());
  }

  public List<WebsiteResponse> getUserWebsites() {
    User user = usersService.getCurrentUser();
    List<Website> websites = repository.getUserWebsites(user);
    return websites.stream()
        .map(website -> new WebsiteResponse(website.getId(), website.getName(), website.getUrl()))
        .collect(Collectors.toList());
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

  public WebsiteResponse createWebsite(Website website) {
    User user = usersService.getCurrentUser();
    website.setOwner(user);
    Website savedWebsite = repository.createWebsite(website);
    return new WebsiteResponse(savedWebsite.getId(), savedWebsite.getName(), savedWebsite.getUrl());
  }
}