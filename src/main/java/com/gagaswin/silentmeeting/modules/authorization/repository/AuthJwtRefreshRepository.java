package com.gagaswin.silentmeeting.modules.authorization.repository;

import com.gagaswin.silentmeeting.modules.authorization.model.entity.AuthJwtRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthJwtRefreshRepository extends JpaRepository<AuthJwtRefresh, String> {
  Optional<AuthJwtRefresh> findByRefreshToken(String refreshToken);
}
