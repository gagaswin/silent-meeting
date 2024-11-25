package com.gagaswin.silentmeeting.modules.agenda.entity;

import com.gagaswin.silentmeeting.modules.ideas.entity.Ideas;
import com.gagaswin.silentmeeting.modules.meetings.model.entity.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agenda")
public class Agenda {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "title", length = 100)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

//  RELATION
  @ManyToOne
  @JoinColumn(name = "meeting_id", nullable = false)
  private Meeting meeting;

  @OneToMany(mappedBy = "agenda")
  private List<Ideas> ideas;

  @OneToMany(mappedBy = "agenda")
  private List<Vote> votes;
}
