package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
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
  @JoinColumn(name = "participant_id", nullable = false)
  private Participant participant;

  @ManyToOne
  @JoinColumn(name = "agenda_id", nullable = false)
  private Agenda agenda;
}
