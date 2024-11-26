package com.gagaswin.silentmeeting.modules.users.model.entity;

import com.gagaswin.silentmeeting.modules.agenda.entity.Vote;
import com.gagaswin.silentmeeting.modules.ideas.entity.Ideas;
import com.gagaswin.silentmeeting.modules.participants.entity.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "username", nullable = false, length = 100, unique = true)
  private String username;

  @Column(name = "email", nullable = false, length = 320, unique = true)
  private String email;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

//  RELATION
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserDetail userDetail;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  List<Role> roles;

  @OneToMany(mappedBy = "user")
  private List<Participant> participant;

  @OneToMany(mappedBy = "user")
  private List<Ideas> ideas;

  @OneToMany(mappedBy = "user")
  private List<Vote> votes;
}
