package com.GB.Application.controller;

import com.GB.Application.dto.ChangePasswordDto;
import com.GB.Application.model.User;
import com.GB.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "Operations related to authenticated users")
public class PasswordController {

    private final UserService userService;

    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Change the authenticated user's password",
            description = "Allows an authenticated user to change their password with the current and new password provided.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password changed successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to change password due to incorrect current password or invalid input"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
            }
    )
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        // Get the authenticated user ID from the Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();

        // Attempt to change the password
        boolean isPasswordChanged = userService.changePassword(
                userId,
                changePasswordDto.getCurrentPassword(),
                changePasswordDto.getNewPassword()
        );

        // Return appropriate response
        if (isPasswordChanged) {
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Failed to change password"));
        }
    }
}
