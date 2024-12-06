package com.gagaswin.silentmeeting.modules.users.controller;

import com.gagaswin.silentmeeting.common.response.CommonResponse;
import com.gagaswin.silentmeeting.modules.users.model.request.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.modules.users.model.response.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.modules.users.model.response.UserResponseDto;
import com.gagaswin.silentmeeting.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<CommonResponse<UserResponseDto>> getProfile(Authentication authentication) {
    UserResponseDto user = userService.getProfile(authentication);

    CommonResponse<UserResponseDto> userResponse = CommonResponse.<UserResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(user)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }

  @PutMapping("/update")
  public ResponseEntity<CommonResponse<UpdateUserProfileResponseDto>> update(
      Authentication authentication,
      @RequestBody @Validated UpdateUserProfileRequestDto updateUserProfileRequestDto) {
    UpdateUserProfileResponseDto updatedUser = userService.updateProfile(authentication, updateUserProfileRequestDto);

    CommonResponse<UpdateUserProfileResponseDto> userResponse = CommonResponse.<UpdateUserProfileResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(updatedUser)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }
}
