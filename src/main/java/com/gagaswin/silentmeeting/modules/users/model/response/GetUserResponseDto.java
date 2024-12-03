package com.gagaswin.silentmeeting.modules.users.model.response;

import com.gagaswin.silentmeeting.common.constant.ELastEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponseDto {
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private LocalDate dateOfBirth;
  private String address;
  private String company;
  private ELastEducation lastEducation;
  private String lastInstitutionName;
}
