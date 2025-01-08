package com.gagaswin.silentmeeting.services.data.impl;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.repository.AgendaRepository;
import com.gagaswin.silentmeeting.services.data.AgendaDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaDataServiceImpl implements AgendaDataService {
  private final AgendaRepository agendaRepository;

  @Override
  public void save(Agenda agenda) {
    agendaRepository.save(agenda);
  }
}
