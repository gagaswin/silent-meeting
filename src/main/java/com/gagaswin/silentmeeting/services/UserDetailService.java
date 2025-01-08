package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.entity.UserDetail;

import java.util.Optional;

public interface UserDetailService {
  UserDetail save(UserDetail userDetail);

  Optional<UserDetail> getByEmail(String email);
}
