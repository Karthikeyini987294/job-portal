
package com.jobportal.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.RegisterRequest;
import com.jobportal.dto.UserResponse;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponse registerUser(RegisterRequest request) {

        // Check duplicate email
        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt password using BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign default role
        user.setRole("CANDIDATE");

        // Save user
        User savedUser = userRepository.save(user);

        // Return safe response without password
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

public UserResponse loginUser(LoginRequest request) {

    // Find user by email
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid email or password"));

    // Verify password using BCrypt
    boolean passwordMatches = passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
    );

    if (!passwordMatches) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    // Return safe user response
    return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
    );
}

    
   
}


