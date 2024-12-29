package com.gagaswin.silentmeeting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EParticipantVote {
  LIKE("Like"),
  NEUTRAL("Neutral"),
  DISLIKE("Dislike");

  private final String displayName;
}
