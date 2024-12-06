package com.gagaswin.silentmeeting.modules.auth.repository;

import com.gagaswin.silentmeeting.modules.auth.model.entity.AuthJwtRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthJwtRefreshRepository extends JpaRepository<AuthJwtRefresh, String> {
  Optional<AuthJwtRefresh> findByRefreshToken(String refreshToken);
}
