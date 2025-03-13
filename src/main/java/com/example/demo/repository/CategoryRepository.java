package com.example.demo.repository;


import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  List<Category> findByOwnerIsNull();

  List<Category> findByUsersContaining(User user);

  Optional<Category> findByIdAndUsersContaining(Long id, User user);
}
