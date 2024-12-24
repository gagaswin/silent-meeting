package com.gagaswin.silentmeeting.models.dtos.agenda;

import com.gagaswin.silentmeeting.models.entity.Meeting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgendaRequestDto {
  @NotBlank
  @Size(min = 1, max = 100)
  private String title;

  private String description;
}
