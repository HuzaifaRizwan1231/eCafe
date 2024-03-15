package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "student")

public class Student {
    @Column(name = "name")
    private String name;
}
