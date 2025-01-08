package com.gagaswin.silentmeeting.services.data;

import com.gagaswin.silentmeeting.models.entity.UserDetail;

import java.util.Optional;

public interface UserDetailDataService {
  UserDetail save(UserDetail userDetail);

  Optional<UserDetail> findByEmail(String email);
}
