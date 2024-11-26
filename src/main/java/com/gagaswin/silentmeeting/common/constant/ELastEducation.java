package com.gagaswin.silentmeeting.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ELastEducation {
  PRIMARY_SCHOOL("Primary School"),
  MIDDLE_SCHOOL("Middle School / Junior High School"),
  HIGH_SCHOOL("High School / Senior High School"),
  VOCATIONAL_SCHOOL("Vocational School"),
  DIPLOMA("Diploma (D1, D2, D3)"),
  BACHELORS_DEGREE("Bachelor's Degree (S1)"),
  MASTERS_DEGREE("Master's Degree (S2)"),
  DOCTORATE_DEGREE("Doctorate Degree (S3)"),
  PROFESSIONAL_CERTIFICATION("Professional Certification"),
  POST_DOCTORATE("Post-Doctorate");

  private final String displayName;
}
