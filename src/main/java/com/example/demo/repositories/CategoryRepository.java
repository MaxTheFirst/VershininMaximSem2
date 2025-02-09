package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.User;

import java.util.Set;

public interface CategoryRepository {
  Set<Category> getAllCategory();
  Set<Category> getDefaultCategories();
  Set<Category> getUserCategories(User user);
  void createCategory(Category category);
}
