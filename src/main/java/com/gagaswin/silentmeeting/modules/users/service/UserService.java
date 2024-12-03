package com.gagaswin.silentmeeting.modules.users.service;

import com.gagaswin.silentmeeting.modules.users.model.response.GetUserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
  GetUserResponseDto getUserById(String id) throws UsernameNotFoundException;
}
