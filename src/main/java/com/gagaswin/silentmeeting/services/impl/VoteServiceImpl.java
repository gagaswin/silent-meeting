package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Vote;
import com.gagaswin.silentmeeting.services.VoteService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {
  @Override
  public CountVoteResponseDto countVote(Agenda agenda) {
    Map<EParticipantVote, Long> voteCounts = agenda.getVotes().stream()
        .collect(Collectors.groupingBy(Vote::getParticipantVote, Collectors.counting()));

    return CountVoteResponseDto.builder()
        .likeCount(voteCounts.getOrDefault(EParticipantVote.LIKE, 0L))
        .neutralCount(voteCounts.getOrDefault(EParticipantVote.NEUTRAL, 0L))
        .dislikeCount(voteCounts.getOrDefault(EParticipantVote.DISLIKE, 0L))
        .build();
  }
}
