package com.GB.Application.controller;

import com.GB.Application.dto.UserDto;
import com.GB.Application.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

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

        return ResponseEntity.ok(dto);
    }


}
