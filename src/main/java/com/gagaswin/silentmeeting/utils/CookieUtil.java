package com.gagaswin.silentmeeting.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
  @Value("${silent-meeting.configuration.cookie.max-age}")
  private long cookieMaxAge;

  @Value("${silent-meeting.configuration.cookie.http-only.max-age}")
  private long cookieHttpOnlyMaxAge;

  public ResponseCookie createCookie(String name, String value) {
    return ResponseCookie.from(name, value)
        .httpOnly(false)
        .secure(false)
        .path("/")
        .maxAge(cookieMaxAge)
        .build();
  }

  public ResponseCookie createHttpOnlyCookie(String name, String value) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(cookieHttpOnlyMaxAge)
        .build();
  }

  public void addCookieToResponse(HttpServletResponse servletResponse, ResponseCookie cookie){
    servletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }
}
