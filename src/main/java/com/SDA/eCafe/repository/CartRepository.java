package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
}

