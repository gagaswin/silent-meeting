package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.CreateAgendaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.GetAgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaDto;
import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.AgendaRepository;
import com.gagaswin.silentmeeting.services.AgendaService;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.UserService;
import com.gagaswin.silentmeeting.services.VoteService;
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
  private final VoteService voteService;

  @Override
  public Agenda findByIdOrThrow(String id) {
    return agendaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Agenda", "Id", id));
  }

  @Override
  public AgendaResponseDto create(Authentication authentication,
                                  String meetingId,
                                  CreateAgendaRequestDto createAgendaRequestDto) throws BadRequestException {
    User currentUser = userService.getUserAuth(authentication);
    Meeting currentMeeting = meetingService.findByIdOrThrow(meetingId);

    if (!currentUser.getId().equals(currentMeeting.getUser().getId())) {
      throw new BadRequestException("You are not the host!");
    }

    Agenda agenda = Agenda.builder()
        .title(createAgendaRequestDto.getTitle())
        .description(createAgendaRequestDto.getDescription())
        .createdAt(LocalDateTime.now())
        .meeting(currentMeeting)
        .build();
    Agenda saveAgenda = agendaRepository.save(agenda);

    return AgendaResponseDto.builder()
        .id(saveAgenda.getId())
        .title(saveAgenda.getTitle())
        .description(saveAgenda.getDescription())
        .createdAt(saveAgenda.getCreatedAt())
        .meetingId(saveAgenda.getMeeting().getId())
        .build();
  }

  @Override
  public List<GetAgendaResponseDto> getAll(Authentication authentication, String meetingId) throws BadRequestException {
    User currentUser = userService.getUserAuth(authentication);
    Meeting currentMeeting = meetingService.findByIdOrThrow(meetingId);

    boolean isCreator = currentMeeting.getUser().getId().equals(currentUser.getId());
    boolean isParticipant = currentMeeting.getParticipants().stream()
        .anyMatch(participant -> participant.getUser().getId().equals(currentUser.getId()));

    if (!isCreator && !isParticipant) throw new BadRequestException("User is not part of this meeting");

    return currentMeeting.getAgenda().stream()
        .map(agenda -> {
          CountVoteResponseDto countVoteResponseDto = voteService.countVote(agenda);
          return GetAgendaResponseDto.builder()
              .id(agenda.getId())
              .meetingId(agenda.getMeeting().getId())
              .title(agenda.getTitle())
              .description(agenda.getDescription())
              .createdAt(agenda.getCreatedAt())
              .votes(countVoteResponseDto)
              .build();
        })
        .toList();
  }

  @Override
  public GetAgendaResponseDto get(Authentication authentication, String meetingId, String agendaId) throws BadRequestException {
    User currentUser = userService.getUserAuth(authentication);
    Meeting currentMeeting = meetingService.findByIdOrThrow(meetingId);

    boolean isCreator = currentMeeting.getUser().getId().equals(currentUser.getId());
    boolean isParticipant = currentMeeting.getParticipants().stream()
        .anyMatch(participant -> participant.getUser().getId().equals(currentUser.getId()));

    if (!isCreator && !isParticipant) throw new BadRequestException("User is not part of this meeting");

    Agenda currentAgenda = this.findByIdOrThrow(agendaId);

    CountVoteResponseDto countVoteResponseDto = voteService.countVote(currentAgenda);
    List<IdeaDto> ideaDtos = currentAgenda.getIdeas().stream()
        .map(ideas -> IdeaDto.builder()
            .id(ideas.getId())
            .content(ideas.getContent())
            .participantId(ideas.getParticipant().getId())
            .createdAt(ideas.getCreatedAt())
            .build())
        .toList();

    return GetAgendaResponseDto.builder()
        .id(currentAgenda.getId())
        .meetingId(currentAgenda.getMeeting().getId())
        .title(currentAgenda.getTitle())
        .description(currentAgenda.getDescription())
        .createdAt(currentAgenda.getCreatedAt())
        .votes(countVoteResponseDto)
        .ideas(ideaDtos)
        .build();
  }
}
