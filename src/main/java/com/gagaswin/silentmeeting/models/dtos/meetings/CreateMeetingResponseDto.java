package com.gagaswin.silentmeeting.models.dtos.meetings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingResponseDto {
  private String title;
  private String meetingId;
  private String password;
}
