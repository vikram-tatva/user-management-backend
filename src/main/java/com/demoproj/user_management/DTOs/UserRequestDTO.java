package com.demoproj.user_management.DTOs;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String roleId;
}
