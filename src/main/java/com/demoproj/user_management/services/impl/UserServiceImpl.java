package com.demoproj.user_management.services.impl;

import com.demoproj.user_management.DTOs.SearchDTO;
import com.demoproj.user_management.DTOs.UserRequestDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.entities.User;
import com.demoproj.user_management.exceptions.BusinessRuleException;
import com.demoproj.user_management.exceptions.ResourceNotFoundException;
import com.demoproj.user_management.repositories.UserRepository;
import com.demoproj.user_management.services.UserService;
import com.demoproj.user_management.utils.UserSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

        this.validateUser(user, "", false);

        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserRequestDTO userRequestDTO) {
        validateUser(modelMapper.map(userRequestDTO, User.class), userId, true);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by userId: " + userId));

        userRequestDTO.setPassword(user.getPassword());
        modelMapper.map(userRequestDTO, user);
        user.setUpdatedDate(LocalDateTime.now());
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(SearchDTO search) {
        List<User> users;
        Sort sort;

        //Default Pagination
        search.setPageNo((search.getPageNo() <= 0 ? 1 : search.getPageNo()) - 1);
        search.setPageSize(search.getPageSize() <= 0 ? 10 : search.getPageSize());

        //Default Sort
        sort = Sort.by(Sort.Direction.DESC, "firstName");

        if (search.getResultOrder() != null){
            sort = search.getResultOrder().getOrderDirection().equalsIgnoreCase("DESC")
                    ? Sort.by(search.getResultOrder().getColumn()).descending()
                    : Sort.by(search.getResultOrder().getColumn()).ascending();
        }

        Pageable pageInfo = PageRequest.of(search.getPageNo(), search.getPageSize(),  sort);
        Specification<User> specification = UserSpecification.build(search.getFilters(), search.getSearch());

        users = userRepository.findAll(specification, pageInfo).getContent();

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

    private void validateUser(User user,
                              @DefaultValue("") String userId,
                              @DefaultValue("false") boolean isUpdate) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()
            && (!isUpdate || !Objects.equals(existingUser.get().getId(), userId))) {
            throw new BusinessRuleException("Username already exists");
        }

        existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()
            && (!isUpdate || !existingUser.get().getId().equals(userId))){
            throw new BusinessRuleException("Email already exists");
        }

        existingUser =  userRepository.findByMobile(user.getMobile());
        if (existingUser.isPresent()
            &&  (!isUpdate || !existingUser.get().getId().equals(userId))){
            throw new BusinessRuleException("Mobile already exists");
        }
    }
}
