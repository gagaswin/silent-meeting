package com.gagaswin.silentmeeting.modules.users.repository;

import com.gagaswin.silentmeeting.modules.users.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
  Optional<UserDetail> findByUserId(String userId);

  Optional<UserDetail> findByEmail(String email);
}
