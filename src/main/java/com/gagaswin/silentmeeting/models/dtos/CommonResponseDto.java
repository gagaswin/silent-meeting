package com.gagaswin.silentmeeting.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto<T> {
  private Integer statusCode;
  private String message;
  private T data;
}
