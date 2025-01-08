package com.gagaswin.silentmeeting.models.dtos.agenda;

import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaDto;
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
public class GetAgendaResponseDto {
  private String id;
  private String meetingId;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private CountVoteResponseDto votes;
  private List<IdeaDto> ideas;
}
