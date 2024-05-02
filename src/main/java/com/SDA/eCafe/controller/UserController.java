package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.User;
import com.SDA.eCafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "LoginRegister";
    }

    @PostMapping("/loggingIn")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            // Retrieve the user input from the login form
            String email = user.getEmail();
            String password = user.getPassword();
            String role = user.getRole();
            System.out.println(email);
            System.out.println(password);
            System.out.println(role);
            // Check if a user with the provided email exists in the database
            Optional<User> existingUser = userRepository.findByEmail(email);

            if (existingUser.isPresent()) {

                // User exists, check if the password matches
                User storedUser = existingUser.get();
                if (storedUser.getPassword().equals(password)) {
                    if (storedUser.getRole().equals(role)) {
                        int userId = storedUser.getUserId();
                        Cookie cookie = new Cookie("userId", String.valueOf(userId));
                        response.addCookie(cookie);
                        if (role == "Admin") {
                            return "redirect:/admin";
                        } else if (role == "Clerk") {
                            return "redirect:/clerk";
                        } else if (role == "Manager") {
                            return "redirect:/manager";
                        } else {
                            return "redirect:/";
                        }
                    } else {
                    model.addAttribute("message", "Check your role again");
                    return "LoginRegister";
                    }
                } else {
                    model.addAttribute("message", "Incorrect password");
                    return "LoginRegister";
                }
            } else {
                model.addAttribute("message", "User does not exist");
                return "LoginRegister";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
        // Validate user input
        try {
            // Setters in the User model will throw exceptions if data is invalid
            user.setRole("Customer");
        } catch (IllegalArgumentException e) {
            // If validation fails, return to registration form with error messages
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        // Save the user to the database
        userRepository.save(user);

        // Redirect to login page after successful registration
        return "redirect:/login";
    }
}
