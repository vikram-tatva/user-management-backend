package com.demoproj.user_management.services.impl;

import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.entities.User;
import com.demoproj.user_management.exceptions.ResourceNotFoundException;
import com.demoproj.user_management.repositories.UserRepository;
import com.demoproj.user_management.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private  final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by userId: " + userId));
        user.setUpdatedDate(LocalDateTime.now());
        modelMapper.map(userRequestDTO, user);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by userId: " + userId));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(String userId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found by userId: " + userId);
        }

        userRepository.deleteById(userId);
    }
}
