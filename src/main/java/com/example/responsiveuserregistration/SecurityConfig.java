package com.example.responsiveuserregistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity, consider enabling for production
                .authorizeRequests()
                .requestMatchers("/register", "/api/auth/register", "/login", "/favicon.ico")
                .permitAll() // Allow unauthenticated access to specified endpoints
                .requestMatchers("/index") // Protect the index page
                .authenticated() // Require authentication for accessing /index
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .formLogin()
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/index", true) // Redirect to index after successful login
                .failureUrl("/login?error=true") // Redirect back with error on failure
                .permitAll() // Allow everyone to access the login page
                .and()
                .logout()
                .logoutUrl("/api/auth/logout") // Specify the logout URL
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                .invalidateHttpSession(true) // Invalidate session on logout
                .deleteCookies("JSESSIONID") // Remove the JSESSIONID cookie
                .permitAll(); // Allow everyone to access logout

        return http.build();
    }
}
