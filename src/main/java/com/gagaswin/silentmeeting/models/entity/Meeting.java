package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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

  @Column(name = "start_time", nullable = false)
  private Date startTime;

  @Column(name = "end_time", nullable = false)
  private Date endTime;

  @Column(name = "allow_anonymous", nullable = false)
  private Boolean allowAnonymous;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

//  RELATION
  @OneToMany(mappedBy = "meeting")
  private List<Participant> participants;

  @OneToMany(mappedBy = "meeting")
  private List<Agenda> agenda;
}
