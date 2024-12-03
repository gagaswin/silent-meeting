package com.gagaswin.silentmeeting.modules.authorization.controller;

import com.gagaswin.silentmeeting.common.response.CommonResponse;
import com.gagaswin.silentmeeting.common.utils.CookieUtil;
import com.gagaswin.silentmeeting.modules.authorization.model.request.LoginRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.request.RefreshTokenRequest;
import com.gagaswin.silentmeeting.modules.authorization.model.request.RegisterRequestDto;
import com.gagaswin.silentmeeting.modules.authorization.model.response.AuthResponse;
import com.gagaswin.silentmeeting.modules.authorization.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final CookieUtil cookieUtil;

  @PostMapping("/register")
  public ResponseEntity<CommonResponse<AuthResponse>> registerUser(
      @RequestBody RegisterRequestDto registerRequestDto,
      HttpServletResponse servletResponse) {
    AuthResponse registerResponse = authService.register(registerRequestDto);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", registerResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createHttpOnlyCookie("refreshToken", registerResponse.getRefreshToken()));

    CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
        .statusCode(HttpStatus.CREATED.value())
        .message("Register success !!!")
        .data(registerResponse)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<CommonResponse<AuthResponse>> loginUser(
      @RequestBody LoginRequestDto loginRequestDto,
      HttpServletResponse servletResponse) {
    AuthResponse loginResponse = authService.login(loginRequestDto);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", loginResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createHttpOnlyCookie("refreshToken", loginResponse.getRefreshToken()));

    CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
        .statusCode(HttpStatus.OK.value())
        .message("Login success !!!")
        .data(loginResponse)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<CommonResponse<AuthResponse>> refreshToken(
      @RequestBody RefreshTokenRequest refreshTokenRequest,
      HttpServletResponse servletResponse) {
    String refreshToken = refreshTokenRequest.getRefreshToken();
    AuthResponse refreshRotationResponse = authService.refresh(refreshToken);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", refreshRotationResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("refreshToken", refreshRotationResponse.getRefreshToken()));

    CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
        .statusCode(HttpStatus.OK.value())
        .data(refreshRotationResponse)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
