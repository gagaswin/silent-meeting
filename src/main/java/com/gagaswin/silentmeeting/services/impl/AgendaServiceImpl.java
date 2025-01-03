package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.CreateAgendaRequestDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.AgendaRepository;
import com.gagaswin.silentmeeting.services.AgendaService;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {
  private final AgendaRepository agendaRepository;
  private final UserService userService;
  private final MeetingService meetingService;

  @Override
  public Agenda getCurrentAgenda(String id) {
    return agendaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Agenda", "Id", id));
  }

  @Override
  public AgendaResponseDto create(Authentication authentication,
                                  String meetingId,
                                  CreateAgendaRequestDto createAgendaRequestDto) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);

    if (!currentUser.getId().equals(currentMeeting.getUser().getId())) {
      throw new BadRequestException("You are not the host!");
    }

    LocalDateTime currentTime = LocalDateTime.now();
    Agenda agenda = Agenda.builder()
        .title(createAgendaRequestDto.getTitle())
        .description(createAgendaRequestDto.getDescription())
        .createdAt(currentTime)
        .meeting(currentMeeting)
        .build();
    agendaRepository.save(agenda);

    return AgendaResponseDto.builder()
        .title(createAgendaRequestDto.getTitle())
        .description(createAgendaRequestDto.getDescription())
        .createdAt(currentTime)
        .meetingId(currentMeeting.getId())
        .build();
  }

  @Override
  public List<AgendaResponseDto> getAll(Authentication authentication, String meetingId) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);

    boolean isCreator = currentMeeting.getUser().getId().equals(currentUser.getId());
    boolean isParticipant = currentMeeting.getParticipants().stream()
        .anyMatch(participant -> participant.getUser().getId().equals(currentUser.getId()));

    if (!isCreator && !isParticipant) throw new BadRequestException("User is not part of this meeting");

    return currentMeeting.getAgenda().stream()
        .map(agenda -> AgendaResponseDto.builder()
            .title(agenda.getTitle())
            .description(agenda.getDescription())
            .createdAt(agenda.getCreatedAt())
            .meetingId(agenda.getMeeting().getId())
            .build())
        .toList();
  }

  @Override
  public AgendaResponseDto get(Authentication authentication, String meetingId, String agendaId) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);

    boolean isCreator = currentMeeting.getUser().getId().equals(currentUser.getId());
    boolean isParticipant = currentMeeting.getParticipants().stream()
        .anyMatch(participant -> participant.getUser().getId().equals(currentUser.getId()));

    if (!isCreator && !isParticipant) throw new BadRequestException("User is not part of this meeting");

    Agenda currentAgenda = this.getCurrentAgenda(agendaId);

    return AgendaResponseDto.builder()
        .title(currentAgenda.getTitle())
        .description(currentAgenda.getDescription())
        .createdAt(currentAgenda.getCreatedAt())
        .meetingId(currentAgenda.getMeeting().getId())
        .build();
  }
}
