package com.library.inventory.controller;

import com.library.inventory.model.User;
import com.library.inventory.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // Base path for user operations
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Registration endpoint ---
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role if not provided
        if (user.getRole() == null) {
            user.setRole("READER");
        }

        // Save user to DB
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
