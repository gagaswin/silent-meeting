package com.gagaswin.silentmeeting.models.dtos.votes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountVoteResponseDto {
  private long likeCount;
  private long neutralCount;
  private long dislikeCount;
}
