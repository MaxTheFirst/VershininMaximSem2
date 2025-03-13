package com.example.demo.repository;


import com.example.demo.domain.model.User;
import com.example.demo.domain.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
  List<Website> findByOwnerIsNull();

  List<Website> findByUsersContaining(User user);

  Optional<Website> findByIdAndUsersContaining(Long id, User user);

  ;
}
