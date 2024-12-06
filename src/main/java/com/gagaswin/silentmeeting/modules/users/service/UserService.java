package com.gagaswin.silentmeeting.modules.users.service;

import com.gagaswin.silentmeeting.modules.users.model.request.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.modules.users.model.response.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.modules.users.model.response.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
  UserResponseDto getProfile(Authentication authentication) throws UsernameNotFoundException;

  UpdateUserProfileResponseDto updateProfile(Authentication authentication, UpdateUserProfileRequestDto updateUserProfileRequestDto);
}
