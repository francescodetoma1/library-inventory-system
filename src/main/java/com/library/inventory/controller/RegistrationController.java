package com.library.inventory.controller;

import com.library.inventory.model.User;
import com.library.inventory.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // This method shows the registration page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "admin/register";
    }

    // This method handles the form submission
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            // Check if username already exists
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                model.addAttribute("error", "Username already exists!");
                return "admin/register";
            }

            // Encode password for security
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Always assign ROLE_READER on registration
            user.setRole("ROLE_READER");

            userRepository.save(user);

            // Redirect to login after success
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "admin/register";
        }
    }
}