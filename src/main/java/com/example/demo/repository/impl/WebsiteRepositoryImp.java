package com.example.demo.repository.impl;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import com.example.demo.repository.WebsiteRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class WebsiteRepositoryImp implements WebsiteRepository {

  private final EntityManager entityManager;

  @Override
  public List<Website> getDefaultWebsite() {
    return entityManager.createQuery("SELECT w FROM Website w WHERE w.owner IS NULL", Website.class).getResultList();
  }

  @Override
  public List<Website> getUserWebsite(User user) {
    return entityManager.createQuery("SELECT w FROM Website w JOIN w.users u WHERE u = :user", Website.class)
        .setParameter("user", user)
        .getResultList();
  }

  @Override
  public void chooseCategory(User user, Website website) {
    user.getWebsites().add(website);
    entityManager.merge(user);
  }

  @Override
  @Transactional
  public Website createWebsite(Website website) {
    entityManager.persist(website);
    return website;
  }

  @Override
  public Optional<Website> findById(Long id) {
    return Optional.ofNullable(entityManager.find(Website.class, id));
  }
}
