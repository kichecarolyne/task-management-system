package com.zendev.task_management_system.user.controllers;

import com.zendev.task_management_system.dto.ReqRes;
import com.zendev.task_management_system.user.services.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserManagement userManagement;

    // User registration
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        return ResponseEntity.ok(userManagement.register(reg));
    }

    // User login
    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagement.login(req));
    }

    // Refresh token
    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagement.refreshToken(req));
    }

    // Update user profile
    @PutMapping("/user/update-my-profile")
    public ResponseEntity<ReqRes> updateUserProfile(@RequestBody ReqRes reqRes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userManagement.updateProfile(email, reqRes));
    }

    // Get user profile
    @GetMapping("/user/get-my-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = userManagement.getMyProfile(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Delete user account
    @DeleteMapping("/user/delete")
    public ResponseEntity<ReqRes> deleteUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userManagement.deleteUserAccount(email));
    }
}
