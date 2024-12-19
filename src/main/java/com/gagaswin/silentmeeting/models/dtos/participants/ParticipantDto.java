package com.gagaswin.silentmeeting.models.dtos.participants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
  private String id;
  private LocalDateTime joinedAt;
  private String username;
}
