package com.example.demo.domain.dto.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq")
  @SequenceGenerator(name = "article_id_seq", sequenceName = "article_id_seq", allocationSize = 1)
  @Column(name = "article_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 2000)
  private String description;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false, unique = true)
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "website_id", nullable = false) // Статья принадлежит сайту
  private Website website;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false) // Статья привязана к категории
  private Category category;
}
