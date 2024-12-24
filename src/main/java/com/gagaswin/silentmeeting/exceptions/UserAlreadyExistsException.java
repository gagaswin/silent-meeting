package com.gagaswin.silentmeeting.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
  private final String fieldName;
  private final String fieldValue;

  public UserAlreadyExistsException(String fieldName, String fieldValue) {
    super(String.format("%s \"%s\" already exist", fieldName, fieldValue));
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}
