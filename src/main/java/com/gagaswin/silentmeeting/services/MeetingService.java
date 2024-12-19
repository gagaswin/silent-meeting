package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.MeetingResponseDto;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface MeetingService {
  Meeting getMeetingById(String id);

  CreateMeetingResponseDto create(Authentication authentication, CreateMeetingRequestDto createMeetingRequestDto);

  MeetingResponseDto get(Authentication authentication, String meetingId) throws BadRequestException;
}
