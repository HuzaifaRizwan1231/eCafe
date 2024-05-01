package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.Feedback;
import com.SDA.eCafe.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.time.LocalDateTime;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Inject UserRepository and ProductRepository if needed

    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        // Assuming you have methods to get all users and products from UserRepository and ProductRepository
        // List<User> users = userRepository.findAll();
        // List<Product> products = productRepository.findAll();

        // For simplicity, assuming the user and product IDs are hardcoded here
        int userID = 1; // Example user ID
        int productID = 1; // Example product ID

        // Pass user and product IDs to the model
        model.addAttribute("userID", userID);
        model.addAttribute("productID", productID);

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

        // Return the name of the confirmation view
        return "/";
    }
}
