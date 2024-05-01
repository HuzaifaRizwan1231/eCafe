package com.SDA.eCafe.model;

import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Orders{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "ProductId")
    private Integer ProductId;

    @Column(name = "UserId")
    private Integer UserId;

    @Column(name = "Quantity")
    private Integer Quantity;

    @Column(name = "ProductPrice")
    private Integer ProductPrice;

    @Column(name = "Date")
    private Date Date;

    @Column(name = "Status")
    private String Status;

    @Column(name = "PaymentMethod")
    private String PaymentMethod;

    @Column(name = "PickupTime")
    private Time PickupTime;

    // Getters and setters...

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Integer productPrice) {
        ProductPrice = productPrice;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public Time getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(Time pickupTime) {
        PickupTime = pickupTime;
    }
}