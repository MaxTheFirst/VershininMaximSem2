package com.example.demo.repositories;

import com.example.demo.models.User;
import com.example.demo.models.Website;

import java.util.Set;

public interface WebsiteRepository {
  Set<Website> getDefaultWebsite();
  Set<Website> getUserWebsite(User user);
  Website createWebsite(Website website);
}
