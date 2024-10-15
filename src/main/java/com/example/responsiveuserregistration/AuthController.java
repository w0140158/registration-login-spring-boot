package com.example.responsiveuserregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Returns the Thymeleaf template for registration
    }
    @PostMapping("/api/auth/register")
    public ResponseEntity<String> registerUser(@ModelAttribute User user) {
        if (userService.registerUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Returns the Thymeleaf template for login
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        if (userService.login(username, password)) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
    }

}
