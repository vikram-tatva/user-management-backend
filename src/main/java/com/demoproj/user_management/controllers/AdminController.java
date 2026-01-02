package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userRequestDTO));
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return  ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
