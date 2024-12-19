package com.gagaswin.silentmeeting.models.dtos.meetings;

import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaDto;
import com.gagaswin.silentmeeting.models.dtos.participants.ParticipantDto;
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
public class MeetingResponseDto {
  private String id;
  private String title;
  private String description;
  private String password;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean allowAnonymous;
  private LocalDateTime createdAt;
  private String createdBy;
  private List<ParticipantDto> participants;
  private List<AgendaDto> agenda;
}
