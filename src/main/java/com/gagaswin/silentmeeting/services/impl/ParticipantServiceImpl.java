package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.MeetingNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.ParticipantRepository;
import com.gagaswin.silentmeeting.services.MeetingService;
import com.gagaswin.silentmeeting.services.ParticipantService;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final ParticipantRepository participantRepository;
  private final UserService userService;
  private final MeetingService meetingService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JoinMeetingResponseDto joinMeeting(Authentication authentication,
                                            JoinMeetingRequestDto joinMeetingRequestDto) throws BadRequestException {
    User currentUser = userService.currentUserAuth(authentication);

    Meeting currentMeeting = meetingService.getMeetingById(joinMeetingRequestDto.getMeetingId());

    if (!passwordEncoder.matches(joinMeetingRequestDto.getMeetingPassword(), currentMeeting.getPassword())) {
      throw new MeetingNotFoundException("Meeting not found");
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
}
