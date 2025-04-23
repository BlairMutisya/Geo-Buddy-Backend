package com.GB.Application.config;

import com.GB.Application.model.User;
import com.GB.Application.model.Role;
import com.GB.Application.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeederConfig {

    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Admin 1 details
            String admin1Email = "tonnyblair09@gmail.com";
            if (!userRepository.existsByEmail(admin1Email)) {
                User admin1 = new User();
                admin1.setUsername("superadmin1");
                admin1.setEmail(admin1Email);
                admin1.setPassword(passwordEncoder.encode("admin123")); // Change later
                admin1.setPhoneNumber("0712345678");
                admin1.setRole(Role.ADMIN);
                admin1.setEnabled(true);
                userRepository.save(admin1);

                System.out.println("Admin 1 created: " + admin1Email);
            } else {
                System.out.println("Admin 1 already exists: " + admin1Email);
            }

            // Admin 2 details
            String admin2Email = "dennismutuma77@gmail.com";
            if (!userRepository.existsByEmail(admin2Email)) {
                User admin2 = new User();
                admin2.setUsername("superadmin2");
                admin2.setEmail(admin2Email);
                admin2.setPassword(passwordEncoder.encode("admin123")); // Change later
                admin2.setPhoneNumber("0787654321");
                admin2.setRole(Role.ADMIN);
                admin2.setEnabled(true);
                userRepository.save(admin2);

                System.out.println("Admin 2 created: " + admin2Email);
            } else {
                System.out.println("Admin 2 already exists: " + admin2Email);
            }
        };
    }
}
