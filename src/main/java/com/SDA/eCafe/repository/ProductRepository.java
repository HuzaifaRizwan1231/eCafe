package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.Name) LIKE %:keyword% OR LOWER(p.Description) LIKE %:keyword%")
    List<Product> findProductsByKeywordIgnoreCase(@Param("keyword") String keyword);
   
    @Query("SELECT p FROM Product p WHERE p.Category = ?1")
    List<Product> findByCategory(String category);

    @Query("SELECT p.Name, SUM(o.Quantity*o.ProductPrice) AS TotalSale  FROM Orders o JOIN Product p ON p.ID = o.ProductId WHERE o.Status = 'Completed' GROUP BY o.ProductId")
    List<Object[]> getAllProductsWithSale();

    @Modifying
    @Query("UPDATE Product p SET p.Status = :status WHERE p.ID = :productId")
    int updateProductStatus(@Param("status") String status, @Param("productId") Integer productId);
    
    
}
