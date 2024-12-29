package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "username", nullable = false, length = 100, unique = true)
  private String username;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at")
  private LocalDateTime updatedAt = LocalDateTime.now();

//  RELATION
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private UserDetail userDetail;

  @OneToMany(mappedBy = "user")
  private List<Meeting> meetings;

  @OneToMany(mappedBy = "user")
  private List<Participant> participant;

//  @OneToMany(mappedBy = "user")
//  private List<Ideas> ideas;

//  @OneToMany(mappedBy = "user")
//  private List<Vote> votes;

  @OneToMany(mappedBy = "user")
  private List<AuthJwtRefresh> authJwtRefreshes;
}
