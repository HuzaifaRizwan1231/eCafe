package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Product;
import com.SDA.eCafe.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
