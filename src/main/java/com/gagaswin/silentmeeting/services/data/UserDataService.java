package com.gagaswin.silentmeeting.services.data;

import com.gagaswin.silentmeeting.models.entity.User;

import java.util.Optional;

public interface UserDataService {
  void save(User user);

  Optional<User> findByUsername(String username);
}
