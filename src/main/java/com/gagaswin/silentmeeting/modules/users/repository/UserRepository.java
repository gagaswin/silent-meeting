package com.gagaswin.silentmeeting.modules.users.repository;

import com.gagaswin.silentmeeting.modules.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  @Query("SELECT u " +
      "FROM User u " +
      "LEFT JOIN FETCH u.userDetail " +
      "WHERE u.id = :id")
    // id must match with @Param("id")
  Optional<User> findByIdWithDetail(@Param("id") String id);
}
