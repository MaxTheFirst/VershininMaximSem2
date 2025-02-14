package com.example.demo.repository;

import com.example.demo.domain.dto.model.User;
import com.example.demo.domain.dto.model.Website;

import java.util.Set;

public interface WebsiteRepository {
  Set<Website> getDefaultWebsite();
  Set<Website> getUserWebsite(User user);
  Website createWebsite(Website website);
}
