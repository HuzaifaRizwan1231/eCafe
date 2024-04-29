package com.SDA.eCafe.model;

import java.io.Serializable;

public class CartId implements Serializable {
    private Integer UserId;
    private Integer ProductId;

    public CartId() {
    }

    public CartId(Integer UserId, Integer ProductId) {
        this.UserId = UserId;
        this.ProductId = ProductId;
    }
}