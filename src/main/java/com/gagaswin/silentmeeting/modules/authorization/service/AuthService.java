package com.gagaswin.silentmeeting.modules.authorization.service;

import com.gagaswin.silentmeeting.modules.authorization.model.request.LoginRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.request.RegisterRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.response.AuthResponse;

public interface AuthService {
  AuthResponse register(RegisterRequestDto registerRequestDto);

  AuthResponse login(LoginRequestDto loginRequestDto);

  AuthResponse refresh(String refreshToken);
}
