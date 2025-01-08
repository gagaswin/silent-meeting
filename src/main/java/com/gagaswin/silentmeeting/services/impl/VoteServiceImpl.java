package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.enums.EParticipantVote;
import com.gagaswin.silentmeeting.models.dtos.votes.CountVoteResponseDto;
import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.Vote;
import com.gagaswin.silentmeeting.repository.VoteRepository;
import com.gagaswin.silentmeeting.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
  private final VoteRepository voteRepository;

  @Override
  public Optional<Vote> getByParticipantAndAgenda(Participant participant, Agenda agenda) {
    return voteRepository.findByParticipantAndAgenda(participant, agenda);
  }

  @Override
  public void save(Vote vote) {
    voteRepository.save(vote);
  }

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
