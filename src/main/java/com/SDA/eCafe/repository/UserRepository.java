package com.SDA.eCafe.repository;
import com.SDA.eCafe.model.User;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.Email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}

