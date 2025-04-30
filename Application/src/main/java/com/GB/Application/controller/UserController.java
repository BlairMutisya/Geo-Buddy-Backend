package com.GB.Application.controller;

import com.GB.Application.dto.UserDto;
import com.GB.Application.model.User;
import com.GB.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "Operations related to authenticated users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get the currently authenticated user's profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "User not authenticated")
            }
    )
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }

        User currentUser = (User) authentication.getPrincipal();

        UserDto dto = new UserDto(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getEmail(),
                currentUser.getPhoneNumber()
        );

        return ResponseEntity.ok(dto);
    }

    @Transactional
    @DeleteMapping("/me")
    @Operation(
            summary = "Delete the currently authenticated user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "User not authenticated")
            }
    )
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
