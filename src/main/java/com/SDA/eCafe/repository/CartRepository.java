package com.SDA.eCafe.repository;


import java.util.*;
import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
     @Query("SELECT c, p FROM Cart c JOIN Product p ON c.ProductId = p.ID WHERE c.UserId = :userId")
    List<Object[]> findCartOfProductByUserId(@Param("userId") Integer userId);
}

