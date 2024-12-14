package com.gagaswin.silentmeeting.controllers;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileRequestDto;
import com.gagaswin.silentmeeting.models.dtos.users.UpdateUserProfileResponseDto;
import com.gagaswin.silentmeeting.models.dtos.users.UserResponseDto;
import com.gagaswin.silentmeeting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<CommonResponseDto<UserResponseDto>> getProfile(Authentication authentication) {
    UserResponseDto user = userService.getProfile(authentication);

    CommonResponseDto<UserResponseDto> userResponse = CommonResponseDto.<UserResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(user)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }

  @PutMapping
  public ResponseEntity<CommonResponseDto<UpdateUserProfileResponseDto>> update(
      Authentication authentication,
      @RequestBody @Validated UpdateUserProfileRequestDto updateUserProfileRequestDto) {
    UpdateUserProfileResponseDto updatedUser = userService.updateProfile(authentication, updateUserProfileRequestDto);

    CommonResponseDto<UpdateUserProfileResponseDto> userResponse = CommonResponseDto.<UpdateUserProfileResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(updatedUser)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }
}
