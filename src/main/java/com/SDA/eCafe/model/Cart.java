package com.SDA.eCafe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import com.SDA.eCafe.model.CartId;

@Entity
@Table(name = "Cart")
@IdClass(CartId.class)
public class Cart {
    @Id
    private Integer userId;

    @Id
    private Integer productId;

    @Column
    private Integer quantity;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}