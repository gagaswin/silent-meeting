package com.gagaswin.silentmeeting.exceptions;

public class MeetingNotFoundException extends RuntimeException {
  public MeetingNotFoundException(String message) {
    super(message);
  }
}
