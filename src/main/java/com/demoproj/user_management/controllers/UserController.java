package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService  userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO userRequestDTO) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(id, userRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
}
