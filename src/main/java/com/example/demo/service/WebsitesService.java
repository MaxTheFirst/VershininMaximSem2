package com.example.demo.service;

import com.example.demo.domain.dto.response.WebsiteResponse;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebsitesService {

  private final WebsiteRepository repository;
  private final UsersService usersService;

  @Transactional(readOnly = true)
  public List<WebsiteResponse> getDefaultWebsites() {
    List<Website> websites = repository.findByOwnerIsNull();
    return websites.stream()
        .map(website -> new WebsiteResponse(website.getId(), website.getName(), website.getUrl()))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<WebsiteResponse> getUserWebsites() {
    User user = usersService.getCurrentUser();
    List<Website> websites = repository.findByUsersContaining(user);
    return websites.stream()
        .map(website -> new WebsiteResponse(website.getId(), website.getName(), website.getUrl()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void chooseWebsite(Long websiteId) {
    Optional<Website> website = repository.findById(websiteId);
    if (website.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Website with this ID not found");
    }
    User user = usersService.getCurrentUser();
    user.getWebsites().add(website.get());
    usersService.save(user);
  }

  @Transactional
  public void removeWebsite(Long websiteId) {
    User user = usersService.getCurrentUser();
    Optional<Website> website = repository.findByIdAndUsersContaining(websiteId, user);
    if (website.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Website with this ID not found");
    }
    user.getWebsites().remove(website.get());
    usersService.save(user);
  }

  @Transactional
  public WebsiteResponse createWebsite(Website website) {
    User user = usersService.getCurrentUser();
    website.setOwner(user);
    repository.save(website);
    return new WebsiteResponse(website.getId(), website.getName(), website.getUrl());
  }
}