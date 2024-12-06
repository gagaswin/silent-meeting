package com.gagaswin.silentmeeting.models.dtos.users;

import com.gagaswin.silentmeeting.enums.ELastEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
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
  private LocalDateTime createdAt;
}
