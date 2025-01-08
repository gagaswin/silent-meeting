package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.CreateAgendaRequestDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.GetAgendaResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AgendaService {
  Agenda findByIdOrThrow(String id);

  AgendaResponseDto create(Authentication authentication,
                           String meetingId,
                           CreateAgendaRequestDto createAgendaRequestDto) throws BadRequestException;

  List<GetAgendaResponseDto> getAll(Authentication authentication, String meetingId) throws BadRequestException;

  GetAgendaResponseDto get(Authentication authentication, String meetingId, String agendaId) throws BadRequestException;
}
