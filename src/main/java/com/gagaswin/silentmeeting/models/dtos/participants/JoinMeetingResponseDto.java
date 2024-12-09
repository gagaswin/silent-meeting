package com.gagaswin.silentmeeting.models.dtos.participants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinMeetingResponseDto {
  private String meetingTitle;
  private String meetingDescription;
}
