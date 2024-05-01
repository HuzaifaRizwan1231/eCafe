package com.SDA.eCafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {

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
        // setEmail("");
        // setName("");
        // setPassword("");
        // setContact(0);
        // setAddress("");
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

    

    // // Getters and Setters
    // public int getUserId() {
    //     return userId;
    // }

    // public void setUserId(int userId) {
    //     this.userId = userId;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     if (isValidEmail(email)) {
    //         this.email = email;
    //     } else {
    //         throw new IllegalArgumentException("Invalid email format");
    //     }
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     if (isValidPassword(password)) {
    //         this.password = password;
    //     } else {
    //         throw new IllegalArgumentException("Password must contain at least one lowercase and one uppercase letter");
    //     }
    // }

    // public String getAddress() {
    //     return address;
    // }

    // public void setAddress(String address) {
    //     this.address = address;
    // }

    // public long getContact() {
    //     return contact;
    // }

    // public void setContact(long contact) {
    //     if (isValidContact(contact)) {
    //         this.contact = contact;
    //     } else {
    //         throw new IllegalArgumentException("Contact number must have 11 digits");
    //     }
    // }

    // public UserRole getRole() {
    //     return role;
    // }

    // public void setRole(UserRole role) {
    //     this.role = role;
    // }

    // // Basic email validation method
    // private boolean isValidEmail(String email) {
    //     return email != null && email.matches("^(.+)@(.+)$");
    // }

    // // Password validation method
    // private boolean isValidPassword(String password) {
    //     return password != null && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*");
    // }

    // // Basic contact number validation method
    // private boolean isValidContact(long contact) {
    //     String contactString = String.valueOf(contact);
    //     return contactString != null && contactString.length() == 11 && contactString.matches("\\d+");
    // }
}