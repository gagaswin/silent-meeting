package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.ideas.CreateIdeaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.entity.*;
import com.gagaswin.silentmeeting.repository.IdeasRepository;
import com.gagaswin.silentmeeting.repository.ParticipantRepository;
import com.gagaswin.silentmeeting.services.AgendaService;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.ParticipantService;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final ParticipantRepository participantRepository;
  private final IdeasRepository ideasRepository;
  private final UserService userService;
  private final MeetingService meetingService;
  private final AgendaService agendaService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JoinMeetingResponseDto joinMeeting(Authentication authentication,
                                            JoinMeetingRequestDto joinMeetingRequestDto) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(joinMeetingRequestDto.getMeetingId());

    if (!passwordEncoder.matches(joinMeetingRequestDto.getMeetingPassword(), currentMeeting.getPassword())) {
      throw new ResourceNotFoundException("Meeting", "Id or Password", "id or password wrong");
    } else if (currentUser.equals(currentMeeting.getUser())) {
      throw new BadRequestException("You are the host at this meeting bro");
    }

    Participant participant = Participant.builder()
        .user(currentUser)
        .meeting(currentMeeting)
        .joinedAt(LocalDateTime.now())
        .build();
    participantRepository.save(participant);

    return JoinMeetingResponseDto.builder()
        .meetingTitle(currentMeeting.getTitle())
        .meetingDescription(currentMeeting.getDescription())
        .build();
  }

  @Override
  public IdeaResponseDto createIdeaContent(Authentication authentication, String meetingId, String agendaId, CreateIdeaRequestDto createIdeaRequestDto) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);
    Agenda currentAgenda = agendaService.getCurrentAgenda(agendaId);

    boolean isHost = currentMeeting.getUser().getId().equals(currentUser.getId());
    if (isHost) throw new BadRequestException("You are the host bro");

    Participant participant = currentMeeting.getParticipants().stream()
        .filter(p -> p.getUser().getId().equals(currentUser.getId()))
        .findFirst()
        .orElseThrow(() -> new BadRequestException("You are not a participant in this meeting."));

    if (LocalDateTime.now().isBefore(currentMeeting.getStartTime())) {
      throw new BadRequestException("The meeting has not started yet");
    } else if (LocalDateTime.now().isAfter(currentMeeting.getEndTime())) {
      throw new BadRequestException("The meeting is over");
    }

    Ideas ideas = Ideas.builder()
        .content(createIdeaRequestDto.getContent())
        .createdAt(LocalDateTime.now())
        .participant(participant)
        .agenda(currentAgenda)
        .build();
    ideasRepository.save(ideas);

    return IdeaResponseDto.builder()
        .id(ideas.getId())
        .content(ideas.getContent())
        .build();
  }
}
