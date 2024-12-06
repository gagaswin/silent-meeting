package com.gagaswin.silentmeeting.repository;

import com.gagaswin.silentmeeting.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

//  Optional<User> findByEmail(String email);

//  @Query("SELECT u " +
//      "FROM User u " +
//      "LEFT JOIN FETCH u.userDetail " +
//      "WHERE u.username = :username")
//    // username must match with @Param("username")
//  Optional<User> findByIdWithDetail(@Param("username") String username);

}
