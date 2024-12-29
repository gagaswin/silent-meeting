package com.gagaswin.silentmeeting.models.entity;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vote")
public class Vote {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Enumerated(EnumType.STRING)
  @Column(name = "participant_vote")
  private EParticipantVote participantVote;

//  RELATION
  @ManyToOne
  @JoinColumn(name = "participant_id")
  private Participant participant;

  @ManyToOne
  @JoinColumn(name = "agenda_id")
  private Agenda agenda;
}
