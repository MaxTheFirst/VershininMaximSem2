package com.example.demo.service;

import com.example.demo.domain.dto.response.CategoryResponse;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import com.example.demo.repository.CategoryRepository;
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
public class CategoriesService {

  private final CategoryRepository repository;
  private final UsersService usersService;

  public List<CategoryResponse> getDefaultCategories() {
    List<Category> categories = repository.findByOwnerIsNull();
    return categories.stream()
        .map(category -> new CategoryResponse(category.getId(), category.getName()))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<CategoryResponse> getUserCategories() {
    User user = usersService.getCurrentUser();
    List<Category> categories = repository.findByUsersContaining(user);
    return categories.stream()
        .map(category -> new CategoryResponse(category.getId(), category.getName()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void chooseCategory(Long categoryId) {
    Optional<Category> category = repository.findById(categoryId);
    if (category.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with this ID not found");
    }
    User user = usersService.getCurrentUser();
    user.getCategories().add(category.get());
    usersService.save(user);
  }

  @Transactional
  public void removeCategory(Long categoryId) {
    User user = usersService.getCurrentUser();
    Optional<Category> category = repository.findByIdAndUsersContaining(categoryId, user);
    if (category.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with this ID not found");
    }
    user.getCategories().remove(category.get());
    usersService.save(user);
  }

  public CategoryResponse createCategory(Category category) {
    User user = usersService.getCurrentUser();
    category.setOwner(user);
    repository.save(category);
    return new CategoryResponse(category.getId(), category.getName());
  }
}