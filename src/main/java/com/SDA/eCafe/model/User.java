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
    private Integer UserId;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Contact")
    private String Contact;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Password")
    private String Password;

    @Column(name = "Address")
    private String Address;

    @Column(name = "Role")
    private String Role;

    public void setUserId(Integer UserId) {
       this. UserId = UserId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

   

    public Integer getUserId() {
        return UserId;
    }

    public String getName() {
        return Name;
    }
    public String getContact() {
        return Contact;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return Role;
    }

    public String getAddress() {
        return Address;
    }

    
}