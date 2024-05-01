package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Feedback;
import com.SDA.eCafe.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Method to extract user ID from cookies
    private int getUserIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID")) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        // If user ID is not found, return -1 or any other value that indicates absence of user ID
        return -1;
    }

    @GetMapping("/feedback/{productId}")
    public String showFeedbackForm(@PathVariable("productId") int productId, HttpServletRequest request, Model model) {
        // Get user ID from cookies
        int userId = getUserIdFromCookies(request);
        
        // If user ID is not found, redirect to login page
        if (userId == -1) {
            // You can redirect to a login page with a message prompting the user to log in first
            return "redirect:/login?message=Please login first to provide feedback";
        }

        // Pass user and product IDs to the model
        model.addAttribute("userId", userId);
        model.addAttribute("productId", productId);

        // Add an empty Feedback object to bind the form data
        model.addAttribute("feedback", new Feedback());

        return "FeedbackForm";
    }

    @PostMapping("/submitFeedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, Model model) {
        // Set the current date and time
        feedback.setDate(LocalDateTime.now());

        // Save the feedback to the database
        feedbackRepository.save(feedback);

        // Add a confirmation message to the model
        model.addAttribute("confirmationMessage", "Thank you for your feedback!");

        // Redirect to the home page or any other desired page after submitting feedback
        return "redirect:/";
    }

    @GetMapping("/viewfeedback")
    public String viewFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        model.addAttribute("feedbacks", feedbacks);
        return "ViewFeedback"; 
    }
}
