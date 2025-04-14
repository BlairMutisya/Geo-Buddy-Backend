package com.GB.Application.controller;

import com.GB.Application.dto.LoginUserDto;
import com.GB.Application.dto.RegisterUserDto;
import com.GB.Application.dto.VerifyUserDto;
import com.GB.Application.model.User;
import com.GB.Application.responses.LoginResponse;
import com.GB.Application.service.AuthenticationService;
import com.GB.Application.service.JwtService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//        System.out.println("generatting token");
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return !jwtToken.isEmpty() ?
                ResponseEntity.ok().body(new LoginResponse(jwtToken, "360000")) :
                ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new LoginResponse("Email/Password is incorrect", "1"));
//        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
//            return ResponseEntity.ok("Account verified successfully");
            return ResponseEntity.ok(Map.of("message", "Account verified successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}