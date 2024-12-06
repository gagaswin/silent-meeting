package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ideas")
public class Ideas {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "content", length = 500)
  private String content;

  @Column(name = "created_at", nullable = false)
  LocalDateTime createdAt = LocalDateTime.now();

//  RELATION
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "agenda_id", nullable = false)
  private Agenda agenda;
}
