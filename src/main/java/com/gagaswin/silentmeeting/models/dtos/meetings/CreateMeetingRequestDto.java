package com.gagaswin.silentmeeting.models.dtos.meetings;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingRequestDto {
  private String title;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean allowAnonymous;
  private List<Agenda> agenda;
}
