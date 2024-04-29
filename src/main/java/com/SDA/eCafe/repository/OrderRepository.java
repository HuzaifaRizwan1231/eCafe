package com.SDA.eCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SDA.eCafe.model.Orders;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.UserId = :UserId AND o.Status = 'Pending'")
    List<Orders> findPendingOrdersByUserId(Integer UserId);

    @Query("SELECT o, p FROM Orders o JOIN Product p ON o.ProductId = p.ID WHERE o.UserId = :userId ORDER BY o.Date DESC")
    List<Object[]> findOrdersWithProductByUserId(@Param("userId") Integer userId);

}
