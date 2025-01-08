package com.gagaswin.silentmeeting.models.dtos.ideas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaDto {
  private String id;
  private String content;
  private String participantId;
  private LocalDateTime createdAt;
}
