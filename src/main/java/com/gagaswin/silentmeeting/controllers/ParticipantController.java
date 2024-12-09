package com.gagaswin.silentmeeting.controllers;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/participant")
@RequiredArgsConstructor
public class ParticipantController {
  private final ParticipantService participantService;

  @PostMapping("/join")
  public ResponseEntity<CommonResponseDto<JoinMeetingResponseDto>> joinMeeting(
      Authentication authentication, @RequestBody JoinMeetingRequestDto joinMeetingRequestDto)
      throws BadRequestException {
    JoinMeetingResponseDto joinMeeting = participantService.joinMeeting(authentication, joinMeetingRequestDto);

    CommonResponseDto<JoinMeetingResponseDto> response = CommonResponseDto.<JoinMeetingResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(joinMeeting)
        .build();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
