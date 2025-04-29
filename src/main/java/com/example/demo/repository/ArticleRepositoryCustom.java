package com.example.demo.repository;



import com.example.demo.domain.model.Article;
import com.example.demo.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepositoryCustom {
  List<Article> getNewArticlesForUser(User user);
}
