package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.exceptions.MeetingNotFoundException;
import com.gagaswin.silentmeeting.exceptions.UserNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.entity.Meeting;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.MeetingRepository;
import com.gagaswin.silentmeeting.repository.ParticipantRepository;
import com.gagaswin.silentmeeting.repository.UserRepository;
import com.gagaswin.silentmeeting.services.ParticipantService;
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
  private final UserRepository userRepository;
  private final MeetingRepository meetingRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public JoinMeetingResponseDto joinMeeting(Authentication authentication,
                                            JoinMeetingRequestDto joinMeetingRequestDto) throws BadRequestException {
    User currentUser = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new UserNotFoundException("User not valid"));

    Meeting currentMeeting = meetingRepository.findById(joinMeetingRequestDto.getMeetingId())
        .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));

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
