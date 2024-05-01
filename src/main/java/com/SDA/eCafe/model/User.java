package com.SDA.eCafe.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "User")
public class User{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")

    private Integer userId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Address", nullable = false, length = 250)
    private String address;

    @Column(name = "Contact", nullable = false, precision = 11, scale = 0)
    private long contact;

    // @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private String role;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public long getContact() {
        return contact;
    }

    public String getRole() {
        return role;
    }

    // Constructor
    public User() {
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public void setRole(String role) {
        this.role = role;
    }

 

}