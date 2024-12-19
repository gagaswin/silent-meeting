package com.gagaswin.silentmeeting.controllers;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingRequestDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.CreateMeetingResponseDto;
import com.gagaswin.silentmeeting.models.dtos.meetings.MeetingResponseDto;
import com.gagaswin.silentmeeting.services.MeetingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meetings")
@RequiredArgsConstructor
public class MeetingController {
  private final MeetingService meetingService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<CreateMeetingResponseDto>> createMeeting(Authentication authentication,
                                                                                   @RequestBody CreateMeetingRequestDto createMeetingRequestDto) {
    CreateMeetingResponseDto createMeetingResponseDto = meetingService.create(authentication, createMeetingRequestDto);

    CommonResponseDto<CreateMeetingResponseDto> responseDto = CommonResponseDto.<CreateMeetingResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(createMeetingResponseDto)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @GetMapping("/{meetingId}")
  public ResponseEntity<CommonResponseDto<MeetingResponseDto>> getMeeting(Authentication authentication,
                                                                          @PathVariable String meetingId) throws BadRequestException {
    MeetingResponseDto meetingResponseDto = meetingService.get(authentication, meetingId);

    CommonResponseDto<MeetingResponseDto> responseDto = CommonResponseDto.<MeetingResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(meetingResponseDto)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}
