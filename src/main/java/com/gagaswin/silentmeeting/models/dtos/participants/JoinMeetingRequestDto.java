package com.gagaswin.silentmeeting.models.dtos.participants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinMeetingRequestDto {
  private String meetingId;
  private String meetingPassword;
}
