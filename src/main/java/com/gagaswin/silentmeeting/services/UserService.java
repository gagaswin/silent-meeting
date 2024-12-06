package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
  UserResponseDto getProfile(Authentication authentication) throws UsernameNotFoundException;

  UpdateUserProfileResponseDto updateProfile(Authentication authentication, UpdateUserProfileRequestDto updateUserProfileRequestDto);
}
