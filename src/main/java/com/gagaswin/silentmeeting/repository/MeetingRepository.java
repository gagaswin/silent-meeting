package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String> {

}
