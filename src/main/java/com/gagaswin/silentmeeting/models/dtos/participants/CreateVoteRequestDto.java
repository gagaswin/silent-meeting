package com.gagaswin.silentmeeting.models.dtos.participants;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoteRequestDto {
  private EParticipantVote participantVote;
}
