package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.models.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User getCurrentUser(Authentication authentication);

  UserResponseDto getProfile(Authentication authentication);

  UpdateUserProfileResponseDto updateProfile(Authentication authentication,
                                             UpdateUserProfileRequestDto updateUserProfileRequestDto);
}
