package com.gagaswin.silentmeeting.models.dtos.ideas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIdeaRequestDto {
  @NotBlank
  @Size(min = 3)
  private String content;
}
