
package com.jobportal.service;

import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
	private final AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public UserResponse registerUser(RegisterRequest request) {

		// Check duplicate email
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Email already registered");
		}
		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("CANDIDATE");
		User savedUser = userRepository.save(user);
		return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
	}

	public UserResponse loginUser(LoginRequest request) {

	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            request.getEmail(),
	            request.getPassword()
	        )
	    );

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() ->
	                new IllegalArgumentException("Invalid email or password"));

	    return new UserResponse(
	            user.getId(),
	            user.getName(),
	            user.getEmail(),
	            user.getRole()
	    );
}
}

