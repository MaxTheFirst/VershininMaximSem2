package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "websites")
@Data
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

  @ManyToMany
  @JoinTable(
      name = "user_websites",
      joinColumns = @JoinColumn(name = "website_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;
}
