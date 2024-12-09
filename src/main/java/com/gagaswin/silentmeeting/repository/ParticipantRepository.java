package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String> {

}
