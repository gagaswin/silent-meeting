package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.MeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.ParticipantDto;
import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.AgendaRepository;
import com.gagaswin.silentmeeting.repository.MeetingRepository;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.UserService;
import com.gagaswin.silentmeeting.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
  private final MeetingRepository meetingRepository;
  private final UserService userService;
  private final AgendaRepository agendaRepository;
  private final VoteService voteService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Meeting getById(String id) {
    return meetingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Meeting", "Id", id));
  }

  @Override
  public CreateMeetingResponseDto create(Authentication authentication, CreateMeetingRequestDto createMeetingRequestDto) {
    User currentUser = userService.getUserAuth(authentication);

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

  @Override
  public MeetingResponseDto get(Authentication authentication, String meetingId) throws BadRequestException {
    User currentUser = userService.getUserAuth(authentication);
    Meeting currentMeeting = this.getById(meetingId);

    boolean isCreator = currentMeeting.getUser().getId().equals(currentUser.getId());
    boolean isParticipant = currentMeeting.getParticipants().stream()
        .anyMatch(participant -> participant.getUser().getId().equals(currentUser.getId()));

    if (!isCreator && !isParticipant) {
      throw new BadRequestException("User is not part of this meeting");
    }

    List<ParticipantDto> participantDtos = currentMeeting.getParticipants().stream()
        .map(participant -> ParticipantDto.builder()
            .id(participant.getId())
            .joinedAt(participant.getJoinedAt())
            .username(participant.getUser().getUsername())
            .build())
        .toList();

    List<AgendaDto> agendaDtos = currentMeeting.getAgenda().stream()
        .map(agenda -> {
          CountVoteResponseDto countVoteResponseDto = voteService.countVote(agenda);
          return AgendaDto.builder()
              .id(agenda.getId())
              .title(agenda.getTitle())
              .description(agenda.getDescription())
              .createdAt(agenda.getCreatedAt())
              .votes(countVoteResponseDto)
              .build();
        })
        .toList();

    MeetingResponseDto.MeetingResponseDtoBuilder responseDto = MeetingResponseDto.builder()
        .id(currentMeeting.getId())
        .title(currentMeeting.getTitle())
        .description(currentMeeting.getDescription())
        .startTime(currentMeeting.getStartTime())
        .endTime(currentMeeting.getEndTime())
        .allowAnonymous(currentMeeting.isAllowAnonymous())
        .createdAt(currentMeeting.getCreatedAt())
        .createdBy(currentMeeting.getUser().getUsername())
        .participants(participantDtos)
        .agenda(agendaDtos);

    if (isCreator) responseDto.password(currentMeeting.getPassword());

    return responseDto.build();
  }
}
