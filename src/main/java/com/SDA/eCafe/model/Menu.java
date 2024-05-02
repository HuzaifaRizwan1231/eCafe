package com.SDA.eCafe.model;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private LocalTime startTime;
  
    private LocalTime endTime;
  
    private LocalTime breakFastTime;
  
    // Getters and Setters (omitted for brevity)
  
    public Menu() {
      // Default Constructor
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setBreakFastTime(LocalTime breakFastTime) {
        this.breakFastTime = breakFastTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getBreakFastTime() {
        return breakFastTime;
    }
  
  }
  