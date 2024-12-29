package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import com.gagaswin.silentmeeting.models.entity.Participant;
import com.gagaswin.silentmeeting.models.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {
  Optional<Vote> findByParticipantAndAgenda(Participant participant, Agenda agenda);
}
