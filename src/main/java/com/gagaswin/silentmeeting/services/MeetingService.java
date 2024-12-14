package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingResponseDto;
import org.springframework.security.core.Authentication;

public interface MeetingService {
  CreateMeetingResponseDto create(Authentication authentication, CreateMeetingRequestDto createMeetingRequestDto);
}
