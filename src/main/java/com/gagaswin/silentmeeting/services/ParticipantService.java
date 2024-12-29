package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.ideas.CreateIdeaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.CreateVoteRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.VoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface ParticipantService {
  Participant getCurrentParticipant(User currentUser, Meeting currentMeeting) throws BadRequestException;

  JoinMeetingResponseDto joinMeeting(Authentication authentication,
                                     JoinMeetingRequestDto joinMeetingRequestDto) throws BadRequestException;

  IdeaResponseDto createIdeaContent(Authentication authentication,
                                    String meetingId,
                                    String agendaId,
                                    CreateIdeaRequestDto createIdeaRequestDto) throws BadRequestException;

  VoteResponseDto createAgendaVote(Authentication authentication,
                                   String meetingId,
                                   String agendaId,
                                   CreateVoteRequestDto createVoteRequestDto) throws BadRequestException;
}
