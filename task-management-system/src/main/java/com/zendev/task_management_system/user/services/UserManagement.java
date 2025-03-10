package com.zendev.task_management_system.user.services;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.entity.User;
import com.zendev.task_management_system.user.utils.JwtUtils;
import com.zendev.task_management_system.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagement {

    @Autowired
    private UserRepository usersRepo;

    @Autowired
    private JwtUtils JwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User registration
    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setRole(registrationRequest.getRole());
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

            User userResult = usersRepo.save(user);

            if (userResult.getId() > 0) {
                resp.setUser(userResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    // User login
    public ReqRes login(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            response.setStatusCode(200);
            response.setMessage("Successfully Logged In");
            response.setToken(JwtUtils.generateToken(user));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // Refresh Jwt token
    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        try {
            String email = JwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = usersRepo.findByEmail(email).orElseThrow();

            if (JwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var jwt = JwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setMessage("Successfully Refreshed Token");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // Delete user account
    public ReqRes deleteUserAccount(String email) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                usersRepo.delete(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    // Get user profile
    public ReqRes getMyProfile(String email) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("Profile fetched successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User profile not found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user profile: " + e.getMessage());
        }
        return reqRes;
    }

    // Update user profile
    public ReqRes updateProfile(String email, ReqRes updateRequest) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                if (updateRequest.getName() != null) {
                    user.setName(updateRequest.getName());
                }
                if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(user.getEmail())) {
                    user.setEmail(updateRequest.getEmail());
                }
                if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
                }

                usersRepo.save(user);

                reqRes.setUser(user);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User profile updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for updating");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user profile: " + e.getMessage());
        }
        return reqRes;
    }
}
