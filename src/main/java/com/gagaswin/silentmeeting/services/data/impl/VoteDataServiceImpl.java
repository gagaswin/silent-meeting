package com.gagaswin.silentmeeting.services.data.impl;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.Vote;
import com.gagaswin.silentmeeting.repository.VoteRepository;
import com.gagaswin.silentmeeting.services.data.VoteDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteDataServiceImpl implements VoteDataService {
  private final VoteRepository voteRepository;

  @Override
  public void save(Vote vote) {
    voteRepository.save(vote);
  }

  @Override
  public Optional<Vote> findByParticipantAndAgenda(Participant participant, Agenda agenda) {
    return voteRepository.findByParticipantAndAgenda(participant, agenda);
  }
}
