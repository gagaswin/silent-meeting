package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.UserNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.AgendaRepository;
import com.gagaswin.silentmeeting.repository.MeetingRepository;
import com.gagaswin.silentmeeting.repository.UserRepository;
import com.gagaswin.silentmeeting.services.MeetingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
  private final UserRepository userRepository;
  private final MeetingRepository meetingRepository;
  private final AgendaRepository agendaRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public CreateMeetingResponseDto create(Authentication authentication,
                                         CreateMeetingRequestDto createMeetingRequestDto) {
    User currentUser = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    String generateRandomPassword = RandomStringUtils
        .random(12, 48, 122, true, true, null, new SecureRandom());

    Meeting meeting = Meeting.builder()
        .title(createMeetingRequestDto.getTitle())
        .description(createMeetingRequestDto.getDescription())
        .password(passwordEncoder.encode(generateRandomPassword))
        .startTime(createMeetingRequestDto.getStartTime())
        .endTime(createMeetingRequestDto.getEndTime())
        .allowAnonymous(createMeetingRequestDto.isAllowAnonymous())
        .createdAt(LocalDateTime.now())
        .user(currentUser)
        .build();
    Meeting saveMeeting = meetingRepository.saveAndFlush(meeting);

    for (Agenda agenda : createMeetingRequestDto.getAgenda()) {
      Agenda saveAgenda = Agenda.builder()
          .title(agenda.getTitle())
          .description(agenda.getDescription())
          .createdAt(LocalDateTime.now())
          .meeting(meeting)
          .build();
      agendaRepository.save(saveAgenda);
    }

    return CreateMeetingResponseDto.builder()
        .title(saveMeeting.getTitle())
        .meetingId(saveMeeting.getId())
        .password(generateRandomPassword)
        .build();
  }
}
