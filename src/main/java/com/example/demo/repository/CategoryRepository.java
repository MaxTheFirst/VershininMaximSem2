package com.example.demo.repository;


import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
  List<Category> getAllCategory(); //все категории
  List<Category> getDefaultCategories(); //категории, где owner_id==null
  List<Category> getUserCategories(User user); //выбранные категории пользователя
  Optional<Category> findById(Long id);
  void chooseCategory(User user, Category category);
  void removeCategory(User user, Category category);
  Category createCategory(Category category);
}
