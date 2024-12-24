package com.gagaswin.silentmeeting.handler;

import com.gagaswin.silentmeeting.exceptions.InvalidRefreshTokenException;
import com.gagaswin.silentmeeting.exceptions.ResourceNotFoundException;
import com.gagaswin.silentmeeting.exceptions.UserAlreadyExistsException;
import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import com.gagaswin.silentmeeting.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalCustomExceptionHandler {
  @ExceptionHandler(exception = InvalidRefreshTokenException.class)
  public ResponseEntity<CommonResponseDto<String>> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseUtil.createResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = UserAlreadyExistsException.class)
  public ResponseEntity<CommonResponseDto<String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ResponseUtil.createResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = ResourceNotFoundException.class)
  public ResponseEntity<CommonResponseDto<String>> handleAgendaNotFoundException(ResourceNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ResponseUtil.createResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }
}
