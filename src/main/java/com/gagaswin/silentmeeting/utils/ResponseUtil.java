package com.gagaswin.silentmeeting.utils;

import com.gagaswin.silentmeeting.models.dtos.CommonResponseDto;

public class ResponseUtil {
  public static <T> CommonResponseDto<T> createResponse(Integer httpStatus, String message) {
    return CommonResponseDto.<T>builder()
        .statusCode(httpStatus)
        .message(message)
        .build();
  }
}
