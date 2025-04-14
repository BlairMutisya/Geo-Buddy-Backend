package com.GB.Application.controller;

import com.GB.Application.dto.ChangePasswordDto;
import com.GB.Application.model.User;
import com.GB.Application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class PasswordController {

    private final UserService userService;

    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    // Password change endpoint
    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        // Get the authenticated user ID from the Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
//        int userFound = (Integer) authentication.getPrincipal();
//
////        if (userFound) {
////            return ResponseEntity.status(400).body("Failed to change password");
////        }

        try {
            // Call the service method to change the password
            boolean isPasswordChanged = userService.changePassword(userId, changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword());

            if (isPasswordChanged) {
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.status(400).body("Failed to change password");
            }
        } catch (RuntimeException e) {
            // Handle the error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
