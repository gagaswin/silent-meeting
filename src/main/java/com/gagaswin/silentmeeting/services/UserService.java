package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.models.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {
  User getUserAuth(Authentication authentication);

  UserResponseDto getProfile(Authentication authentication);

  UpdateUserProfileResponseDto updateProfile(Authentication authentication,
                                             UpdateUserProfileRequestDto updateUserProfileRequestDto);
}
