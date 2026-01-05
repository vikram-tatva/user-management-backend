package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.SearchDTO;
import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService  userService;

    @PostMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestBody SearchDTO search) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers(search));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(id, userRequestDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_GET')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
}
