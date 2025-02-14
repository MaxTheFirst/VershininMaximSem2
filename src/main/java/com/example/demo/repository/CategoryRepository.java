package com.example.demo.repository;

import com.example.demo.domain.dto.model.Category;
import com.example.demo.domain.dto.model.User;

import java.util.Set;

public interface CategoryRepository {
  Set<Category> getAllCategory(); //все категории
  Set<Category> getDefaultCategories(); //категории, где owner_id==null
  Set<Category> getUserCategories(User user); //выбранные категории пользователя
  void createCategory(Category category);
}
