package com.gagaswin.silentmeeting.services.impl;

import com.gagaswin.silentmeeting.models.entity.AppUser;
import com.gagaswin.silentmeeting.models.entity.User;
import com.gagaswin.silentmeeting.services.CustomUserDetailsService;
import com.gagaswin.silentmeeting.services.data.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
  private final UserDataService userDataService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDataService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return AppUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }
}
