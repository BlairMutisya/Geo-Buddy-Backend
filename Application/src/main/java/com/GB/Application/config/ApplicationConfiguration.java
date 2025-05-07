package com.GB.Application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.GB.Application.repository.UserRepository;


@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    // Constructor injection of UserRepository
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


@Bean
public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager bean provided by Spring Security's auto-configuration
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authentication -> {
            // Custom authentication logic if needed, or just delegate to the default mechanism
            return authenticationProvider().authenticate(authentication);
        };
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}