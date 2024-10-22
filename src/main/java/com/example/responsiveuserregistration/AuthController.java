package com.example.responsiveuserregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
        return "register"; // This should return the register.html
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> registerUser(@ModelAttribute User user) {
        if (userService.registerUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        if (userService.authenticateUser(username, password)) {
            // Set authentication in the security context if needed
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }

    @GetMapping("/index")
    public String showIndexPage(Model model) {
        return "index"; // This should return the index.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // This should return the login.html
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful!");
    }
}
