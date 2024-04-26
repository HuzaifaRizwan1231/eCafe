package com.SDA.eCafe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column
    @JsonProperty("Name")
    private String Name;

    @Column(name = "Description")
    @JsonProperty("Description")
    private String Description;

    @Column(name = "Status")
    @JsonProperty("Status")
    private String Status;

    @Column(name = "Image")
    @JsonProperty("Image")
    private String Image;

    @Column(name = "Price")
    @JsonProperty("Price")
    private Integer Price;

    @Column(name = "Category")
    @JsonProperty("Category")
    private String Category;

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getStatus() {
        return Status;
    }

    public String getImage() {
        return Image;
    }

    public Integer getPrice() {
        return Price;
    }

    public String getCategory() {
        return Category;
    }
}