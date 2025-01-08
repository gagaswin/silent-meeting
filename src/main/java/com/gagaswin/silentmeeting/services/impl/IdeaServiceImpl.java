package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.models.entity.Ideas;
import com.gagaswin.silentmeeting.repository.IdeasRepository;
import com.gagaswin.silentmeeting.services.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {
  private final IdeasRepository ideasRepository;

  @Override
  public void save(Ideas ideas) {
    ideasRepository.save(ideas);
  }
}
