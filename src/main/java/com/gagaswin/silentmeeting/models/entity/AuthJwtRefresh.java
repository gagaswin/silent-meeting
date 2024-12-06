package com.gagaswin.silentmeeting.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "auth_jwt_refresh")
public class AuthJwtRefresh {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "refresh_token", unique = true)
  private String refreshToken;

  @Column(name = "issued_at")
  private Date issuedAt;

  @Column(name = "expires_at")
  private Date expiresAt;

  @Column(name = "is_revoked")
  private boolean isRevoked = false;

//  RELATION
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
