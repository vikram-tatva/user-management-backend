package com.demoproj.user_management.DTOs;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private RoleDTO role;
}
