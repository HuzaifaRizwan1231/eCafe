package com.SDA.eCafe.model;

import java.io.Serializable;

public class CartId implements Serializable {
    private Integer userId;
    private Integer productId;

    public CartId() {
    }

    public CartId(Integer userId, Integer productId) {
        this.userId = userId;
        this.productId = productId;
    }
}