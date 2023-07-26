package com.turing.javaproject.repos;

import com.turing.javaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query(value = "select password from usr where id = :id", nativeQuery = true)
    String findPasswordByUserId(@Param("id") Long id);

    List<User> findAllByAvatarUrlNull();
}
