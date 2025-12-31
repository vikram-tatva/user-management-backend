package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.LoginDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.utils.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil  jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jwtUtil.generateToken(login.getUsername()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
