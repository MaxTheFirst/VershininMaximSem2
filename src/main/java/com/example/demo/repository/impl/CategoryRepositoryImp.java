package com.example.demo.repository.impl;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImp implements CategoryRepository {

  private final EntityManager entityManager;

  @Override
  public List<Category> getAllCategory() {
    return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
  }

  @Override
  public List<Category> getDefaultCategories() {
    return entityManager.createQuery("SELECT c FROM Category c WHERE c.owner IS NULL", Category.class).getResultList();
  }

  @Override
  public List<Category> getUserCategories(User user) {
    return entityManager.createQuery("SELECT c FROM Category c JOIN c.users u WHERE u = :user", Category.class)
        .setParameter("user", user)
        .getResultList();
  }

  @Override
  @Transactional
  public void chooseCategory(User user, Category category) {
    user.getCategories().add(category);
    entityManager.merge(user);
  }

  @Override
  @Transactional
  public Category createCategory(Category category) {
    entityManager.persist(category);
    return category;
  }

  @Override
  public Optional<Category> findById(Long id) {
    return Optional.ofNullable(entityManager.find(Category.class, id));
  }
}
