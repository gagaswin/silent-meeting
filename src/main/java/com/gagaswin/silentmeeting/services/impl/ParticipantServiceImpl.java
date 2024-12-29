package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.ideas.CreateIdeaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.CreateVoteRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.VoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.*;
import com.gagaswin.silentmeeting.repository.IdeasRepository;
import com.gagaswin.silentmeeting.repository.ParticipantRepository;
import com.gagaswin.silentmeeting.repository.VoteRepository;
import com.gagaswin.silentmeeting.services.AgendaService;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.ParticipantService;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private static final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);
  private final ParticipantRepository participantRepository;
  private final UserService userService;
  private final MeetingService meetingService;
  private final AgendaService agendaService;
  private final IdeasRepository ideasRepository;
  private final VoteRepository voteRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Participant getCurrentParticipant(User currentUser, Meeting currentMeeting) throws BadRequestException {
    return currentMeeting.getParticipants().stream()
        .filter(participant -> participant.getUser().getId().equals(currentUser.getId()))
        .findFirst()
        .orElseThrow(() -> new BadRequestException("You are not a participant in this meeting"));
  }

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
  public IdeaResponseDto createIdeaContent(Authentication authentication,
                                           String meetingId,
                                           String agendaId,
                                           CreateIdeaRequestDto createIdeaRequestDto) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);
    Agenda currentAgenda = agendaService.getCurrentAgenda(agendaId);

    boolean isHost = currentMeeting.getUser().getId().equals(currentUser.getId());
    if (isHost) throw new BadRequestException("You are the host bro");

    Participant participant = getCurrentParticipant(currentUser, currentMeeting);

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

  @Override
  public VoteResponseDto createAgendaVote(Authentication authentication,
                                          String meetingId,
                                          String agendaId,
                                          CreateVoteRequestDto createVoteRequestDto) throws BadRequestException {
    User currentUser = userService.getCurrentUser(authentication);
    Meeting currentMeeting = meetingService.getCurrentMeeting(meetingId);
    Agenda currentAgenda = agendaService.getCurrentAgenda(agendaId);

    Participant participant = getCurrentParticipant(currentUser, currentMeeting);

    Vote vote = voteRepository.findByParticipantAndAgenda(participant, currentAgenda)
        .orElseGet(() -> Vote.builder()
            .participantVote(createVoteRequestDto.getParticipantVote())
            .participant(participant)
            .agenda(currentAgenda)
            .build());

    boolean isNewVote = vote.getId() == null;
    boolean isUpdatedVote = !vote.getParticipantVote().equals(createVoteRequestDto.getParticipantVote());
    if (isNewVote) {
      voteRepository.save(vote);
      log.info("New vote: {}", vote.getParticipantVote());
    } else if (isUpdatedVote) {
      vote.setParticipantVote(createVoteRequestDto.getParticipantVote());
      voteRepository.save(vote);
      log.info("Updated participant vote: {}", vote.getParticipantVote());
    } else {
      log.info("No changes to participant vote: {}", vote.getParticipantVote());
    }

    return VoteResponseDto.builder()
        .id(vote.getId())
        .participantVote(vote.getParticipantVote())
        .participantId(vote.getParticipant().getId())
        .agendaId(vote.getAgenda().getId())
        .isNewVote(isNewVote)
        .build();
  }
}
