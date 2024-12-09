package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface ParticipantService {
  JoinMeetingResponseDto joinMeeting(Authentication authentication, JoinMeetingRequestDto joinMeetingRequestDto)
      throws BadRequestException;
}
