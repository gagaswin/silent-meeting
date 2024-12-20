package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.agenda.AgendaResponseDto;
import com.gagaswin.silentmeeting.models.dtos.agenda.CreateAgendaRequestDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AgendaService {
  Agenda getAgendaById(String id);

  AgendaResponseDto create(Authentication authentication,
                           String meetingId,
                           CreateAgendaRequestDto createAgendaRequestDto) throws BadRequestException;

  List<AgendaResponseDto> getAll(Authentication authentication, String meetingId) throws BadRequestException;

  AgendaResponseDto get(Authentication authentication, String meetingId, String agendaId) throws BadRequestException;
}
