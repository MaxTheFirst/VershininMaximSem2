package com.example.demo.repository;


import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;

import java.util.List;
import java.util.Optional;

public interface WebsiteRepository {
  List<Website> getDefaultWebsite();
  List<Website> getUserWebsite(User user);
  Optional<Website> findById(Long id);
  void chooseCategory(User user, Website website);
  Website createWebsite(Website website);
}
