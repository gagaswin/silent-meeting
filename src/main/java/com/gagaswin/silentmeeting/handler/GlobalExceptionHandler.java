package com.gagaswin.silentmeeting.handler;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.utils.ResponseUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(exception = AuthenticationException.class)
  public ResponseEntity<CommonResponseDto<String>> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseUtil.createResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = UsernameNotFoundException.class)
  public ResponseEntity<CommonResponseDto<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseUtil.createResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = BadRequestException.class)
  public ResponseEntity<CommonResponseDto<String>> handleBadRequestException(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseUtil.createResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = CredentialsExpiredException.class)
  public ResponseEntity<CommonResponseDto<String>> handleCredentialsExpiredException(CredentialsExpiredException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseUtil.createResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }
}
