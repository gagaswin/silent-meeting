package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.Vote;

import java.util.Optional;

public interface VoteService {
  Optional<Vote> getByParticipantAndAgenda(Participant participant, Agenda agenda);

  void save(Vote vote);

  CountVoteResponseDto countVote(Agenda agenda);
}
