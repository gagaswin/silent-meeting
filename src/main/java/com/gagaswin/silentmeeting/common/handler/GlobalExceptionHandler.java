package com.gagaswin.silentmeeting.common.handler;

import com.gagaswin.silentmeeting.common.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(exception = UserAlreadyExistsException.class)
  public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }
}
