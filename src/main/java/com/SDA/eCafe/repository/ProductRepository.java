package com.SDA.eCafe.repository;

import com.SDA.eCafe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.Name) LIKE %:keyword% OR LOWER(p.Description) LIKE %:keyword%")
    List<Product> findProductsByKeywordIgnoreCase(@Param("keyword") String keyword);
}