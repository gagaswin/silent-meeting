package com.gagaswin.silentmeeting.modules.users.controller;

import com.gagaswin.silentmeeting.common.response.CommonResponse;
import com.gagaswin.silentmeeting.modules.users.model.response.GetUserResponseDto;
import com.gagaswin.silentmeeting.modules.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<CommonResponse<GetUserResponseDto>> getUser(@PathVariable String id) {
    GetUserResponseDto user = userService.getUserById(id);

    CommonResponse<GetUserResponseDto> userResponse = CommonResponse.<GetUserResponseDto>builder()
        .statusCode(HttpStatus.OK.value())
        .data(user)
        .build();

    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
  }
}
