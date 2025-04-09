package com.GB.Application.service;

import com.GB.Application.dto.LoginUserDto;
import com.GB.Application.dto.RegisterUserDto;
import com.GB.Application.dto.VerifyUserDto;
import com.GB.Application.model.User;
import com.GB.Application.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Signup method: User creation with verification code and expiration
    public User signup(RegisterUserDto input) {
        User user = new User(
                input.getUsername(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                input.getPhoneNumber()  // Pass phone number here
        );

        // If user is not enabled, set the verification code and expiration
        if (!user.isEnabled()) {
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        }

        sendVerificationEmail(user);  // Send email with the verification code

        // Save the user after setting the verification code and expiration
        return userRepository.save(user);
    }

    // Authenticate method: Handles user login and re-sends verification code if not enabled
    public User authenticate(LoginUserDto input) {
        // Find the user by email
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If the user is not enabled (i.e., not verified)
        if (!user.isEnabled()) {
            // Generate and set a new verification code and expiration time
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));

            // Save the updated user with the new verification code
            userRepository.save(user);

            // Send verification email again
            sendVerificationEmail(user);

            // Inform the user that their account is not verified
            throw new RuntimeException("Account not verified. A new verification code has been sent to your email.");
        }

        // If the user is verified, proceed with authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return user; // Return the authenticated user
    }

    // Verify method: Mark user as enabled after verifying the verification code
    public void verifyUser(VerifyUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // ✅ Null check before comparing expiration time
            if (user.getVerificationCodeExpiresAt() == null ||
                    user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired. Please request a new one.");
            }

            // Check if verification code matches
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);  // Enable the user after successful verification
                // No need to clear verification code and expiration, they will remain as is
                userRepository.save(user);  // Save the user with updated enabled status
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Method to resend verification code if user is not verified
    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Send verification email with the verification code
    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Utility method to generate a random verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 6-digit verification code
        return String.valueOf(code);
    }
}
