
package com.jobportal.config;

import org.springframework.context.annotation.Bean;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	    .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
            	    .requestMatchers(
            	        "/api/users/register",
            	        "/api/users/login",
            	        "/error"
            	    ).permitAll()
            	    .anyRequest().authenticated()
            	);

        return http.build();
    }
}
