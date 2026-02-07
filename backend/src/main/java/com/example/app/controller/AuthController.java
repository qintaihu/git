package com.example.app.controller;

import com.example.app.entity.User;
import com.example.app.security.JwtUtil;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Login successful");
            response.put("data", new TokenResponse(token, userDetails.getUsername()));

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("code", 401);
            response.put("message", "Invalid username or password");
            response.put("data", null);
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request.getUsername(), request.getPassword(), request.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("code", 201);
            response.put("message", "User registered successfully");
            response.put("data", new UserResponse(user.getId(), user.getUsername(), user.getEmail()));

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 401);
            response.put("message", "Not authenticated");
            response.put("data", null);
            return ResponseEntity.status(401).body(response);
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", new UserResponse(user.getId(), user.getUsername(), user.getEmail()));

        return ResponseEntity.ok(response);
    }

    // Inner classes for request/response
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;

        public RegisterRequest() {}
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class TokenResponse {
        private String token;
        private String username;

        public TokenResponse(String token, String username) {
            this.token = token;
            this.username = username;
        }

        public String getToken() { return token; }
        public String getUsername() { return username; }
    }

    public static class UserResponse {
        private Long id;
        private String username;
        private String email;

        public UserResponse(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }

        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
    }

}
