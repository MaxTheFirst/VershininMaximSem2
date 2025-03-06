package com.example.demo.repository.impl;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import com.example.demo.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {


  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public List<Category> getAllCategory() {
    return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
  }

  @Override
  public List<Category> getDefaultCategories() {
    try {
      restTemplate.getForObject("http://localhost:8080", String.class);
    } catch (RestClientException e) {
      //nothing
    }
    return entityManager.createQuery("SELECT c FROM Category c WHERE c.owner IS NULL", Category.class).getResultList();
  }

  @Override
  public List<Category> getUserCategories(User user) {
    try {
      webClient.get().uri("http://localhost:8080/admin")
              .retrieve()
              .bodyToMono(String.class)
              .block();
    } catch (RuntimeException e) {
      //nothing
    }
    return entityManager.createQuery(
            "SELECT c FROM Category c JOIN c.users u WHERE u = :user", Category.class)
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
  public void removeCategory(User user, Category category) {
    user.getCategories().remove(category);
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
