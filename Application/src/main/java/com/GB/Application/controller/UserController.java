package com.GB.Application.controller;

import com.GB.Application.dto.UserDto;
import com.GB.Application.model.User;
import com.GB.Application.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.status(401).body(null);  // Not authenticated
        }

        User currentUser = (User) authentication.getPrincipal();


        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);  // No user found in context
        }

        UserDto dto = new UserDto(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getPhoneNumber()

        );
        String trace = UUID.randomUUID().toString();
        System.out.println("This user has been called: " + trace);
        return ResponseEntity.ok(dto);
    }

    @Transactional
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok("Your account has been deleted.");
    }
}
