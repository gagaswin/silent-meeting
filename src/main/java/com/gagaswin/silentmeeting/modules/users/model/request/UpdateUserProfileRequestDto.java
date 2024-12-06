package com.gagaswin.silentmeeting.modules.users.model.request;

import com.gagaswin.silentmeeting.common.constant.ELastEducation;
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
