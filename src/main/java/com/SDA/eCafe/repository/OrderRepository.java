package com.SDA.eCafe.repository;


// import org.springframework.data.jdbc.repository.query.Modifying;

import org.springframework.data.jpa.repository.Modifying;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SDA.eCafe.model.Orders;


import java.util.List; 
@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o WHERE o.Status = 'pending' ORDER BY o.Date DESC")
    List<Orders> findAllPendingOrders();
    
    @Query("SELECT o FROM Orders o WHERE o.Status IN ('Completed', 'Canceled') AND o.Status IS NOT NULL ORDER BY o.Date DESC")
    List<Orders> findAllCompletedOrCanceledOrders();
    
    @Modifying
    @Query("UPDATE Orders o SET o.Status = 'Completed' WHERE o.ID = :Id")
    void updateOrderStatusToCompleted(@Param("Id") int Id);



}
