package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.Ideas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeasRepository extends JpaRepository<Ideas, String> {

}
