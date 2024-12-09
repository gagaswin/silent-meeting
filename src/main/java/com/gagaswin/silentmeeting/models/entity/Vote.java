package com.gagaswin.silentmeeting.models.entity;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vote")
public class Vote {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "participant_vote")
  private EParticipantVote participantVote;

//  RELATION
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "agenda_id")
  private Agenda agenda;
}
