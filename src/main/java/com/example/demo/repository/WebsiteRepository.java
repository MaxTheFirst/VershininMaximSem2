package com.example.demo.repository;


import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WebsiteRepository {
  List<Website> getDefaultWebsite();
  List<Website> getUserWebsite(User user);
  Optional<Website> findById(Long id);
  void chooseWebsite(User user, Website website);
  void removeWebsite(User user, Website website);
  Website createWebsite(Website website);
}
