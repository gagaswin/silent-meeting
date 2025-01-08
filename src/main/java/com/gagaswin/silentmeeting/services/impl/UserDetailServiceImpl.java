package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.models.entity.UserDetail;
import com.gagaswin.silentmeeting.repository.UserDetailRepository;
import com.gagaswin.silentmeeting.services.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
  private final UserDetailRepository userDetailRepository;

  @Override
  public UserDetail save(UserDetail userDetail) {
    return userDetailRepository.save(userDetail);
  }

  @Override
  public Optional<UserDetail> getByEmail(String email) {
    return userDetailRepository.findByEmail(email);
  }
}
