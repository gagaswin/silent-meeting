package com.gagaswin.silentmeeting.modules.auth.service;

import com.gagaswin.silentmeeting.modules.auth.model.request.LoginUserRequestDto;
import com.gagaswin.silentmeeting.modules.auth.model.request.RegisterUserRequestDto;
import com.gagaswin.silentmeeting.modules.auth.model.response.AuthResponseDto;

public interface AuthService {
  AuthResponseDto register(RegisterUserRequestDto registerUserRequestDto);

  AuthResponseDto login(LoginUserRequestDto loginUserRequestDto);

  AuthResponseDto refresh(String refreshToken);
}
