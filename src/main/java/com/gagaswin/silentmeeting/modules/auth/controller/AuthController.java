package com.gagaswin.silentmeeting.modules.auth.controller;

import com.gagaswin.silentmeeting.common.response.CommonResponse;
import com.gagaswin.silentmeeting.common.utils.CookieUtil;
import com.gagaswin.silentmeeting.modules.auth.model.request.LoginUserRequestDto;
import com.gagaswin.silentmeeting.modules.auth.model.request.RefreshTokenRequestDto;
import com.gagaswin.silentmeeting.modules.auth.model.request.RegisterUserRequestDto;
import com.gagaswin.silentmeeting.modules.auth.model.response.AuthResponseDto;
import com.gagaswin.silentmeeting.modules.auth.service.AuthService;
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
  public ResponseEntity<CommonResponse<AuthResponseDto>> registerUser(
      @RequestBody RegisterUserRequestDto registerUserRequestDto,
      HttpServletResponse servletResponse) {
    AuthResponseDto registerResponse = authService.register(registerUserRequestDto);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", registerResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createHttpOnlyCookie("refreshToken", registerResponse.getRefreshToken()));

    CommonResponse<AuthResponseDto> response = CommonResponse.<AuthResponseDto>builder()
        .statusCode(HttpStatus.CREATED.value())
        .message("Register success !!!")
        .data(registerResponse)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<CommonResponse<AuthResponseDto>> loginUser(
      @RequestBody LoginUserRequestDto loginUserRequestDto,
      HttpServletResponse servletResponse) {
    AuthResponseDto loginResponse = authService.login(loginUserRequestDto);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", loginResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createHttpOnlyCookie("refreshToken", loginResponse.getRefreshToken()));

    CommonResponse<AuthResponseDto> response = CommonResponse.<AuthResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .message("Login success !!!")
        .data(loginResponse)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<CommonResponse<AuthResponseDto>> refreshToken(
      @RequestBody RefreshTokenRequestDto refreshTokenRequestDto,
      HttpServletResponse servletResponse) {
    String refreshToken = refreshTokenRequestDto.getRefreshToken();
    AuthResponseDto refreshRotationResponse = authService.refresh(refreshToken);

    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("accessToken", refreshRotationResponse.getAccessToken()));
    cookieUtil.addCookieToResponse(servletResponse,
        cookieUtil.createCookie("refreshToken", refreshRotationResponse.getRefreshToken()));

    CommonResponse<AuthResponseDto> response = CommonResponse.<AuthResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(refreshRotationResponse)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
