package com.example.demo.repository;


import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;

import java.util.List;
import java.util.Optional;

public interface WebsiteRepository {
  List<Website> getDefaultWebsites();
  List<Website> getUserWebsites(User user);
  Optional<Website> findById(Long id);
  void chooseWebsite(User user, Website website);
  void removeWebsite(User user, Website website);
  Website createWebsite(Website website);
}
