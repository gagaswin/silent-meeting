package com.gagaswin.silentmeeting.models.dtos.votes;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {
  private String id;
  private EParticipantVote participantVote;
  private String participantId;
  private String agendaId;

  @Builder.Default
  private boolean isNewVote = false;
}
