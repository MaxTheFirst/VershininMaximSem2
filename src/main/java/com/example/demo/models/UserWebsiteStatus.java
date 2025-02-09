package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_website_status")
@Data
public class UserWebsiteStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_website_status_id_seq")
  @SequenceGenerator(name = "user_website_status_id_seq", sequenceName = "user_website_status_id_seq", allocationSize = 1)
  @Column(name = "user_website_status_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "website_id", nullable = false)
  private Website website;

  @Column(name = "last_sent_article_date")
  private LocalDateTime lastSentArticleDate;
}
