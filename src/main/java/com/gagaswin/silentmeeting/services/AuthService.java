package com.gagaswin.silentmeeting.services;

import com.gagaswin.silentmeeting.models.dtos.auth.LoginUserRequestDto;
import com.gagaswin.silentmeeting.models.dtos.auth.RegisterUserRequestDto;
import com.gagaswin.silentmeeting.models.dtos.auth.AuthResponseDto;

public interface AuthService {
  AuthResponseDto register(RegisterUserRequestDto registerUserRequestDto);

  AuthResponseDto login(LoginUserRequestDto loginUserRequestDto);

  AuthResponseDto refresh(String refreshToken);
}
