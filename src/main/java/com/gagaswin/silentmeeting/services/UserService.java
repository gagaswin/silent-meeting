package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.models.entity.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
  Optional<User> getByUsername(String username);

  void save(User user);

  User getUserAuth(Authentication authentication);

  UserResponseDto getProfile(Authentication authentication);

  UpdateUserProfileResponseDto updateProfile(Authentication authentication,
                                             UpdateUserProfileRequestDto updateUserProfileRequestDto);
}
