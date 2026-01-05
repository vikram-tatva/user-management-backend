package com.demoproj.user_management.services;

import com.demoproj.user_management.DTOs.SearchDTO;
import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(String userId, UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers(SearchDTO search);
    UserResponseDTO getUserById(String userId);
    void deleteUser(String userId);
}
