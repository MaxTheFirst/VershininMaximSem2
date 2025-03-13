package com.example.demo.repository;

import com.example.demo.domain.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
}