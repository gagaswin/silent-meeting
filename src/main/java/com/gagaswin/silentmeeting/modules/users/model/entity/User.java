package com.gagaswin.silentmeeting.modules.users.model.entity;

import com.gagaswin.silentmeeting.modules.agenda.entity.Vote;
import com.gagaswin.silentmeeting.modules.ideas.entity.Ideas;
import com.gagaswin.silentmeeting.modules.participants.entity.Participant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, length = 320, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserDetail userDetail;

//  RELATION
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
