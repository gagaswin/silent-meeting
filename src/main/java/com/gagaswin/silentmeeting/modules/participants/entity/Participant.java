package com.gagaswin.silentmeeting.modules.participants.entity;

import com.gagaswin.silentmeeting.modules.meetings.model.entity.Meeting;
import com.gagaswin.silentmeeting.modules.users.model.entity.User;
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
@Table(name = "participant")
public class Participant {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "joined_at", updatable = false)
  private LocalDateTime joinedAt = LocalDateTime.now();

//  RELATION
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "meeting_id", nullable = false)
  private Meeting meeting;
}
