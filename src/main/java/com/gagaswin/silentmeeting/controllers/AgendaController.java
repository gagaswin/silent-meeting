package com.gagaswin.silentmeeting.controllers;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.CreateAgendaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.GetAgendaResponseDto;
import com.gagaswin.silentmeeting.services.AgendaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meetings/{meetingId}/agenda")
@RequiredArgsConstructor
public class AgendaController {
  private final AgendaService agendaService;

  @PostMapping
  public ResponseEntity<CommonResponseDto<AgendaResponseDto>> createAgenda(Authentication authentication,
                                                                           @PathVariable String meetingId,
                                                                           @RequestBody CreateAgendaRequestDto createAgendaRequestDto) throws BadRequestException {
    AgendaResponseDto agendaResponseDto = agendaService.create(authentication, meetingId, createAgendaRequestDto);

    CommonResponseDto<AgendaResponseDto> response = CommonResponseDto.<AgendaResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(agendaResponseDto)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<CommonResponseDto<List<GetAgendaResponseDto>>> getAllAgenda(Authentication authentication,
                                                                                    @PathVariable String meetingId) throws BadRequestException {
    List<GetAgendaResponseDto> agendaResponseDtoList = agendaService.getAll(authentication, meetingId);

    CommonResponseDto<List<GetAgendaResponseDto>> response = CommonResponseDto.<List<GetAgendaResponseDto>>builder()
        .statusCode(HttpStatus.OK.value())
        .data(agendaResponseDtoList)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{agendaId}")
  public ResponseEntity<CommonResponseDto<GetAgendaResponseDto>> getAgenda(Authentication authentication,
                                                                        @PathVariable String meetingId,
                                                                        @PathVariable String agendaId) throws BadRequestException {
    GetAgendaResponseDto agendaResponseDto = agendaService.get(authentication, meetingId, agendaId);

    CommonResponseDto<GetAgendaResponseDto> response = CommonResponseDto.<GetAgendaResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(agendaResponseDto)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
