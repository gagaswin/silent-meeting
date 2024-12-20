package com.gagaswin.silentmeeting.models.dtos.agenda;

import com.gagaswin.silentmeeting.models.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaResponseDto {
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private String meetingId;
}
