package com.SDA.eCafe.controller;

import com.SDA.eCafe.repository.ViewFeedbackRepository;
import com.SDA.eCafe.model.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewFeedbackController {

    private final ViewFeedbackRepository feedbackRepository;

    public ViewFeedbackController(ViewFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/viewfeedback")
    public String viewFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        model.addAttribute("feedbacks", feedbacks);
        return "ViewFeedback"; 
    }
}

