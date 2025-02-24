package com.example.demo.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "websites")
public class Website {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "website_id_seq")
  @SequenceGenerator(name = "website_id_seq", sequenceName = "website_id_seq", allocationSize = 1)
  @Column(name = "website_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false, unique = true)
  private String url;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_websites",
      joinColumns = @JoinColumn(name = "website_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = true)
  private User owner;
}
