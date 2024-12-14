package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting")
public class Meeting {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "title", length = 100, nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "password")
  private String password;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @Column(name = "allow_anonymous", nullable = false)
  private boolean allowAnonymous = false;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

//  RELATION
  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private User user;

  @OneToMany(mappedBy = "meeting")
  private List<Participant> participants;

  @OneToMany(mappedBy = "meeting")
  private List<Agenda> agenda;
}
