package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Description")
    private String Description;

    @Column(name = "Status")
    private String Status;

    @Column(name = "Image")
    private String Image;

    @Column(name = "Category")
    private String Category;
}