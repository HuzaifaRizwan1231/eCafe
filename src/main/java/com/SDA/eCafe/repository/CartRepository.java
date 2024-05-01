package com.SDA.eCafe.repository;


import java.util.*;
import java.util.function.Consumer;

import com.SDA.eCafe.model.Cart;
import com.SDA.eCafe.model.CartId;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
     @Query("SELECT c, p FROM Cart c JOIN Product p ON c.ProductId = p.ID WHERE c.UserId = :userId")
    List<Object[]> findCartOfProductByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.UserId = :userId AND c.ProductId = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    @Modifying
    @Query("UPDATE Cart c SET c.Quantity = :quantity WHERE c.UserId = :userId AND c.ProductId = :productId")
    void updateProductQuantity(
        @Param("quantity") Integer quantity,
        @Param("userId") Integer userId,
        @Param("productId") Integer productId
    );

}

