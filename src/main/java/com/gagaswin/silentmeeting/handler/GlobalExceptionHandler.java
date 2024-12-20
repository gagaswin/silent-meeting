package com.gagaswin.silentmeeting.handler;

import com.gagaswin.silentmeeting.exceptions.AgendaNotFoundException;
import com.gagaswin.silentmeeting.exceptions.MeetingNotFoundException;
import com.gagaswin.silentmeeting.exceptions.UserAlreadyExistsException;
import com.gagaswin.silentmeeting.exceptions.UserNotFoundException;
import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  private CommonResponseDto<String> response(Integer httpStatus, String message) {
    return CommonResponseDto.<String>builder()
        .statusCode(httpStatus)
        .message(message)
        .build();
  }

  @ExceptionHandler(exception = UserAlreadyExistsException.class)
  public ResponseEntity<CommonResponseDto<String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(this.response(HttpStatus.CONFLICT.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = UserNotFoundException.class)
  public ResponseEntity<CommonResponseDto<String>> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(this.response(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = MeetingNotFoundException.class)
  public ResponseEntity<CommonResponseDto<String>> handleMeetingNotFoundException(MeetingNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(this.response(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = BadRequestException.class)
  public ResponseEntity<CommonResponseDto<String>> handleBadRequestException(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(this.response(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(exception = AgendaNotFoundException.class)
  public ResponseEntity<CommonResponseDto<String>> handleAgendaNotFoundException(AgendaNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(this.response(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }
}
