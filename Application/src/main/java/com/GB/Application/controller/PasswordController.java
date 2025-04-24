package com.GB.Application.controller;

import com.GB.Application.dto.ChangePasswordDto;
import com.GB.Application.model.User;
import com.GB.Application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        // Get the authenticated user ID from the Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();

        try {
            boolean isPasswordChanged = userService.changePassword(
                    userId,
                    changePasswordDto.getCurrentPassword(),
                    changePasswordDto.getNewPassword()
            );

            Map<String, String> response = new HashMap<>();

            if (isPasswordChanged) {
                response.put("message", "Password changed successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Failed to change password");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "An unexpected error occurred");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
