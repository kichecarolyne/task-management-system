package com.zendev.task_management_system.user.services;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.User;
import com.zendev.task_management_system.user.repositories.UserRepository;
import com.zendev.task_management_system.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // Register a new user
    public ReqRes registerUser(User user) {
        ReqRes response = new ReqRes();
        try {
            // Check if user already exists
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                response.setStatusCode(400);
                response.setMessage("User already exists with the email: " + user.getEmail());
                return response;
            }

            // Encrypt the password before saving it
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            // Save user to the repository
            User savedUser = userRepository.save(user);
            response.setStatusCode(200);
            response.setMessage("User registered successfully");
            response.setUser(savedUser);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error registering user: " + e.getMessage());
        }
        return response;
    }

    // Authenticate and generate JWT token for the user
    public ReqRes authenticateUser(String email, String password) {
        ReqRes response = new ReqRes();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Check if the password matches
                if (passwordEncoder.matches(password, user.getPassword())) {
                    // Generate JWT token
                    String token = jwtUtils.generateToken(user);
                    response.setStatusCode(200);
                    response.setMessage("Authentication successful");
                    response.setToken(token);
                } else {
                    response.setStatusCode(401);
                    response.setMessage("Invalid credentials");
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found with the email: " + email);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error authenticating user: " + e.getMessage());
        }
        return response;
    }

    // Fetch user details by email
    public ReqRes getUserByEmail(String email) {
        ReqRes response = new ReqRes();
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                response.setStatusCode(200);
                response.setMessage("User found");
                response.setUser(user.get());
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving user: " + e.getMessage());
        }
        return response;
    }

    // Custom UserDetailsService method for Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    // Update user details
    public ReqRes updateUser(Integer userId, User updatedUser) {
        ReqRes response = new ReqRes();
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setName(updatedUser.getName());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setEmail(updatedUser.getEmail());

                // Update the password if provided and not empty
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = userRepository.save(existingUser);
                response.setStatusCode(200);
                response.setMessage("User updated successfully");
                response.setUser(savedUser);
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating user: " + e.getMessage());
        }
        return response;
    }

    // Delete user by ID
    public ReqRes deleteUser(Integer userId) {
        ReqRes response = new ReqRes();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                userRepository.delete(user.get());
                response.setStatusCode(200);
                response.setMessage("User deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }
}
