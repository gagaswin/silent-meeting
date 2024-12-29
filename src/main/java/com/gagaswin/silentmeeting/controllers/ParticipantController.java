package com.gagaswin.silentmeeting.controllers;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.CreateIdeaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.ideas.IdeaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.CreateVoteRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.participants.JoinMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.participants.VoteResponseDto;
import com.gagaswin.silentmeeting.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {
  private final ParticipantService participantService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<JoinMeetingResponseDto>> joinMeeting(Authentication authentication,
                                                                               @RequestBody JoinMeetingRequestDto joinMeetingRequestDto) throws BadRequestException {
    JoinMeetingResponseDto joinMeeting = participantService.joinMeeting(authentication, joinMeetingRequestDto);

    CommonResponseDto<JoinMeetingResponseDto> response = CommonResponseDto.<JoinMeetingResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(joinMeeting)
        .build();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("meetings/{meetingId}/agenda/{agendaId}/ideas")
  public ResponseEntity<CommonResponseDto<IdeaResponseDto>> createIdeaContent(Authentication authentication,
                                                                              @PathVariable String meetingId,
                                                                              @PathVariable String agendaId,
                                                                              @RequestBody CreateIdeaRequestDto createIdeaRequestDto) throws BadRequestException{
    IdeaResponseDto ideaContent = participantService.createIdeaContent(authentication, meetingId, agendaId, createIdeaRequestDto);

    CommonResponseDto<IdeaResponseDto> response = CommonResponseDto.<IdeaResponseDto>builder()
        .statusCode(HttpStatus.CREATED.value())
        .data(ideaContent)
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("meetings/{meetingId}/agenda/{agendaId}/votes")
  public ResponseEntity<CommonResponseDto<VoteResponseDto>> createAgendaVote(Authentication authentication,
                                                                             @PathVariable String meetingId,
                                                                             @PathVariable String agendaId,
                                                                             @RequestBody CreateVoteRequestDto createVoteRequestDto) throws BadRequestException {
    VoteResponseDto agendaVote = participantService.createAgendaVote(authentication, meetingId, agendaId, createVoteRequestDto);

    HttpStatus httpStatus;
    if (agendaVote.isNewVote()) {
      httpStatus = HttpStatus.CREATED;
    } else {
      httpStatus = HttpStatus.OK;
    }

    CommonResponseDto<VoteResponseDto> response = CommonResponseDto.<VoteResponseDto>builder()
        .statusCode(httpStatus.value())
        .data(agendaVote)
        .build();
    return ResponseEntity.status(httpStatus).body(response);
  }
}
