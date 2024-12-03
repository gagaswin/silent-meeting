package com.gagaswin.silentmeeting.modules.authorization.model.request;

import com.gagaswin.silentmeeting.common.constant.ELastEducation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
  @NotBlank(message = "Username cannot be blank")
  @Size(min = 3, max = 100, message = "Username length must be at least 3 characters and a maximum of 100 characters")
  private String username;

  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email not valid")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Size(min = 6, max = 100, message = "Password length must be at least 6 characters and a maximum of 100 characters")
  private String password;

  private String phone;

  private LocalDate dateOfBirth;

  private String address;

  private String company;

  private ELastEducation lastEducation;

  private String lastInstitutionName;
}
