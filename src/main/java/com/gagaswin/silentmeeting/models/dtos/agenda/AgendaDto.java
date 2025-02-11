package com.gagaswin.silentmeeting.models.dtos.agenda;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDto {
  private String id;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private CountVoteResponseDto votes;
}
