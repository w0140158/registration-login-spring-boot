package com.example.responsiveuserregistration;

import com.example.responsiveuserregistration.User;
import com.example.responsiveuserregistration.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false; // User already exists
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        userRepository.save(user); // Save user
        return true;
    }

    public boolean login(String username, String password) {
        // Retrieve the user from the database (pseudo code)
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // Validate the password (use BCryptPasswordEncoder to match the password)
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
