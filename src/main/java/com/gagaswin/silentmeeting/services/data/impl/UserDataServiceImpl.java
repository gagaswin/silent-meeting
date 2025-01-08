package com.gagaswin.silentmeeting.services.data.impl;

import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.repository.UserRepository;
import com.gagaswin.silentmeeting.services.data.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {
  private final UserRepository userRepository;

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
