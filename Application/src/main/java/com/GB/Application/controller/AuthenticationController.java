package com.GB.Application.controller;

import com.GB.Application.dto.LoginUserDto;
import com.GB.Application.dto.RegisterUserDto;
import com.GB.Application.dto.VerifyUserDto;
import com.GB.Application.model.User;
import com.GB.Application.responses.LoginResponse;
import com.GB.Application.service.AuthenticationService;
import com.GB.Application.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
@Tag(name = "Authentication API", description = "Operations related to user authentication, registration, and verification")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Operation(
            summary = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user and generate a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        if (!jwtToken.isEmpty()) {
            return ResponseEntity.ok(new LoginResponse(jwtToken, "360000"));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new LoginResponse("Invalid credentials", "1"));
        }
    }

    @PostMapping("/verify")
    @Operation(
            summary = "Verify a user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account verified successfully"),
                    @ApiResponse(responseCode = "400", description = "Verification failed")
            }
    )
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok(Map.of("message", "Account verified successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/resend")
    @Operation(
            summary = "Resend the verification code to the user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Verification code sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to resend verification code")
            }
    )
    public ResponseEntity<Map<String, String>> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok(Map.of("message", "Verification code sent"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
