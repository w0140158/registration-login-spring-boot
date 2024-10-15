package com.example.responsiveuserregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCheckController {

    private final UserService userService;

    @Autowired
    public UserCheckController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/auth/check-username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = userService.isUsernameTaken(username);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @GetMapping("/api/auth/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.isEmailTaken(email);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @GetMapping("/api/auth/finduser-by-username")
    public ResponseEntity<User> findUserByUsername(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }
}
