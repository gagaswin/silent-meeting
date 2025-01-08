package com.gagaswin.silentmeeting.services.data.impl;

import com.gagaswin.silentmeeting.models.entity.Ideas;
import com.gagaswin.silentmeeting.repository.IdeasRepository;
import com.gagaswin.silentmeeting.services.data.IdeaDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdeaDataServiceImpl implements IdeaDataService {
  private final IdeasRepository ideasRepository;

  @Override
  public void save(Ideas ideas) {
    ideasRepository.save(ideas);
  }
}
