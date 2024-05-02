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

    public void setId(Integer id) {
        Id = id;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public void setProductPrice(Integer productPrice) {
        ProductPrice = productPrice;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setPickupTime(Time pickupTime) {
        PickupTime = pickupTime;
    }

    @Column(name = "Status")
    private String Status;

    @Column(name = "PaymentMethod")
    private String PaymentMethod;

    @Column(name = "PickupTime")

    private java.sql.Time PickupTime;


    public Integer getId() {
        return Id;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public Integer getProductPrice() {
        return ProductPrice;
    }


    public Date getDate() {
        return Date;
    }


    public String getStatus() {
        return Status;
    }


    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public Time getPickupTime() {
        return PickupTime;
    }


}