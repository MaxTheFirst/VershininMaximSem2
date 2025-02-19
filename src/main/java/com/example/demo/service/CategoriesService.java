package com.example.demo.service;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

  private final CategoryRepository repository;
  private final UsersService usersService;

  public List<Category> getAllCategory(){
    return repository.getAllCategory();
  }

  public List<Category> getDefaultCategories(){
    return repository.getDefaultCategories();
  }

  public List<Category> getUserCategories() {
    User user = usersService.getCurrentUser();
    return repository.getUserCategories(user);
  }

  public void chooseCategory(Long categoryId) {
    Optional<Category> category = repository.findById(categoryId);
    if (category.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with this ID not fount");
    }
    User user = usersService.getCurrentUser();
    repository.chooseCategory(user, category.get());
  }

  public Category createCategory(Category category) {
    User user = usersService.getCurrentUser();
    category.setOwner(user);
    return repository.createCategory(category);
  }
}
