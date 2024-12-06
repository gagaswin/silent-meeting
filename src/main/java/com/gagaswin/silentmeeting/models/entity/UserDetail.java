package com.gagaswin.silentmeeting.models.entity;

import com.gagaswin.silentmeeting.enums.ELastEducation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_detail")
@Builder
public class UserDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "email", nullable = false, length = 320, unique = true)
  private String email;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "phone", length = 15)
  private String phone;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "address", length = 150)
  private String address;

  @Column(name = "company", length = 100)
  private String company;

  @Enumerated(EnumType.STRING)
  @Column(name = "last_education")
  private ELastEducation lastEducation;

  @Column(name = "last_institution_name", length = 100)
  private String lastInstitutionName;

//  RELATION
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;
}
