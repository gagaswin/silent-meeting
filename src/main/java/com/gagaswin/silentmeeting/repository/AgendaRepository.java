package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, String> {
}
