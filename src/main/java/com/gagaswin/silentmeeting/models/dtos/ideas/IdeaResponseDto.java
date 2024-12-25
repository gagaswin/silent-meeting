package com.gagaswin.silentmeeting.models.dtos.ideas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaResponseDto {
  private String id;
  private String content;
}
