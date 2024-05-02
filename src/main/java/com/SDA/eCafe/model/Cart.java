package com.SDA.eCafe.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Cart")
@IdClass(CartId.class)
public class Cart {
    @Id
    private Integer UserId;

    @Id
    private Integer ProductId;

    @Column
    private Integer Quantity;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer ProductId) {
        this.ProductId = ProductId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    
}