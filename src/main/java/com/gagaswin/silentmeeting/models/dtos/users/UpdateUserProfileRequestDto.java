package com.gagaswin.silentmeeting.models.dtos.users;

import com.gagaswin.silentmeeting.enums.ELastEducation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequestDto {
  private String firstName;

  private String lastName;

  private String phone;

  private LocalDate dateOfBirth;

  private String address;

  private String company;

  private ELastEducation lastEducation;

  private String lastInstitutionName;
}
