package com.gagaswin.silentmeeting.modules.users.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_detail")
public class UserDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "phone", length = 15)
  private String phone;

  @Column(name = "address", length = 150)
  private String address;

//  RELATION
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
