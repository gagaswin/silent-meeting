package com.gagaswin.silentmeeting.services.data;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.Vote;

import java.util.Optional;

public interface VoteDataService {
  void save(Vote vote);

  Optional<Vote> findByParticipantAndAgenda(Participant participant, Agenda agenda);
}
