package com.SDA.eCafe.controller;

import com.SDA.eCafe.model.User;
import com.SDA.eCafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public String getRoleFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Integer userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
            Optional<User> loggedInUser = userRepository.findById(userId);
            if (!loggedInUser.isEmpty()) {
                System.out.println(loggedInUser.get().getRole());
                return loggedInUser.get().getRole();
            }
        }
        return "noUser";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "LoginRegister";
    }

    @PostMapping("/loggingIn")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // Retrieve the user input from the login form
            String email = user.getEmail();
            String password = user.getPassword();
            String role = user.getRole();
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
                        if ("Admin".equals(role)) {
                            return "redirect:/CurrentOrders";
                        } else if ("Clerk".equals(role)) {
                            return "redirect:/CurrentOrders";
                        } else if ("Manager".equals(role)) {
                            return "redirect:/OrderHistory";
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

    @GetMapping("/adminpanel")
    public String adminPage(Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            model.addAttribute("user", new User());
            return "redirect:/adminpanel/viewAllManagers";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/adminpanel/addManager")
    public String addManagerForm(Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            model.addAttribute("user", new User());
            String role = getRoleFromCookies(request);
            model.addAttribute("role", role);
            return "addManager";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/adminpanel/addClerk")
    public String addClerkForm(Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            model.addAttribute("user", new User());
            String role = getRoleFromCookies(request);
            model.addAttribute("role", role);
            return "addClerk";
        } else {
            return "redirect:/login";
        }
    }

    // FUNCTIONS TO ADD MANAGER AND CLERK
    @PostMapping("/adminpanel/addManager")
    public String addManager(@RequestParam String name, @RequestParam long contact,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String address, Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                User user = new User();
                user.setName(name);
                user.setContact(contact);
                user.setEmail(email);
                user.setPassword(password);
                user.setAddress(address);
                user.setRole("Manager");
                userRepository.save(user);
                model.addAttribute("error", "Successfull!");
                return "Manager";
            } catch (Exception e) {
                model.addAttribute("error", "Something went wrong");
                return "addManager";
            }
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/adminpanel/addClerk")
    public String addClerk(@RequestParam String name, @RequestParam long contact,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String address, Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                User user = new User();
                user.setName(name);
                user.setContact(contact);
                user.setEmail(email);
                user.setPassword(password);
                user.setAddress(address);
                user.setRole("Clerk");
                userRepository.save(user);
                model.addAttribute("error", "Successfull!");
                return "addClerk";
            } catch (Exception e) {
                model.addAttribute("error", "Something went wrong");
                return "addClerk";
            }
        } else {
            return "redirect:/login";
        }
    }

    // FUNCTIONS TO VIEW MANAGERS AND CLERKS
    @GetMapping("/adminpanel/viewAllManagers")
    public String getManagers(Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                List<User> allUsers = userRepository.findAll();
                List<User> managers = allUsers.stream()
                        .filter(user -> "Manager".equals(user.getRole()))
                        .collect(Collectors.toList());
                model.addAttribute("managers", managers);
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "Manager";
            } catch (Exception error) {
                System.out.println("-------------------------------------------------");
                System.out.println(error.getMessage());
                return "error";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/adminpanel/viewAllClerks")
    public String getClerks(Model model, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                List<User> allUsers = userRepository.findAll();
                List<User> clerks = allUsers.stream()
                        .filter(user -> "Clerk".equals(user.getRole()))
                        .collect(Collectors.toList());
                model.addAttribute("clerks", clerks);
                String role = getRoleFromCookies(request);
                model.addAttribute("role", role);
                return "Clerk";
            } catch (Exception error) {
                System.out.println("-------------------------------------------------");
                System.out.println(error.getMessage());
                return "error";
            }
        } else {
            return "redirect:/login";
        }
    }

    // FUNCTIONS TO DELETE MANAGER AND CLERK
    @GetMapping("/adminpanel/deleteManager/{UserId}")
    public String deleteManager(@PathVariable("UserId") Integer UserId, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                userRepository.findById(UserId).ifPresent(userRepository::delete);
                return "redirect:/adminpanel/viewAllManagers";
            } catch (Exception error) {
                System.out.println("-------------------------------------------------");
                System.out.println(error.getMessage());
                return "error";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/adminpanel/deleteClerk/{UserId}")
    public String deleteClerk(@PathVariable("UserId") Integer UserId, HttpServletRequest request) {
        if ("Admin".equals(getRoleFromCookies(request))) {
            try {
                System.out.println(UserId);
                userRepository.findById(UserId).ifPresent(userRepository::delete);
                return "redirect:/adminpanel/viewAllClerks";
            } catch (Exception error) {
                System.out.println(error.getMessage());
                return "error";
            }
        } else {
            return "redirect:/login";
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
        return "redirect:/";
    }
}
