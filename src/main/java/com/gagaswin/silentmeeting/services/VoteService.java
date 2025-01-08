package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;

public interface VoteService {
  CountVoteResponseDto countVote(Agenda agenda);
}
