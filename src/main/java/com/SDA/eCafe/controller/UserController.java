package com.SDA.eCafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.SDA.eCafe.model.User;
import com.SDA.eCafe.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/adminpanel/Managers")
    public String addManagerForm(Model model) {
        model.addAttribute("user", new User());
        return "addManager";
    }

    @PostMapping("/adminpanel/Manager")
    public String addManager(@RequestParam String name, @RequestParam String contact, 
                             @RequestParam String email, @RequestParam String password, 
                             @RequestParam String address) {
        User user = new User();
        user.setName(name);
        user.setContact(contact);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setRole("Manager");
        userRepository.save(user);
        return "redirect:/adminpanel/Manager";
    }

    @GetMapping("/adminpanel/Clerks")
    public String addClerkForm(Model model) {
        model.addAttribute("user", new User());
        return "addClerk";
    }

    @PostMapping("/adminpanel/Clerk")
    public String addClerk(@RequestParam String name, @RequestParam String contact, 
                           @RequestParam String email, @RequestParam String password, 
                           @RequestParam String address) {
        User user = new User();
        user.setName(name);
        user.setContact(contact);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setRole("Clerk");
        userRepository.save(user);
        return "redirect:/adminpanel/Clerk";
    }


    @GetMapping("/adminpanel/Manager")
    public String getManagers(Model model) {
        try {
            List<User> allUsers = userRepository.findAll();
            List<User> managers = allUsers.stream()
                                          .filter(user -> "Manager".equals(user.getRole()))
                                          .collect(Collectors.toList());
            model.addAttribute("users", managers);
            //System.out.println(allUsers);
            return "Manager";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }

    @GetMapping("/adminpanel/Clerk")
    public String getClerks(Model model) {
        try {
            List<User> allUsers = userRepository.findAll();
            List<User> clerks = allUsers.stream()
                                        .filter(user -> "Clerk".equals(user.getRole()))
                                        .collect(Collectors.toList());
            model.addAttribute("users", clerks);
            return "Clerk";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }
    @PostMapping("/adminpanel/deleteManager/{UserId}")
    public String deleteManager(@PathVariable Integer UserId) {
        try {
            userRepository.findById(UserId).ifPresent(userRepository::delete);
            return "redirect:/adminpanel/Manager";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }

    @PostMapping("/adminpanel/deleteClerk/{UserId}")
    public String deleteClerk(@PathVariable Integer UserId) {
        try {
            userRepository.findById(UserId).ifPresent(userRepository::delete);
            return "redirect:/adminpanel/Clerk";
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return "error";
        }
    }
}