package com.SDA.eCafe.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FeedbackID")
    private int feedbackID;

    @Column(name = "UserID")
    private int userID;

    @Column(name = "ProductID")
    private int productID;

    @Column(name = "Text", length = 255)
    private String text;

    @Column(name = "Date")
    private LocalDateTime date;

    public Feedback() {
    }

    public Feedback(int userID, int productID, String text, LocalDateTime date) {
        this.userID = userID;
        this.productID = productID;
        this.text = text;
        this.date = date;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
