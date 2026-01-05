package com.demoproj.user_management.DTOs;

import com.demoproj.user_management.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UserRequestDTO {
    @NotBlank(message = "First name is required.")
    @Size(max = 20, message = "First name should be less then 20 character.")
    private String firstName;

    @Size(max = 20, message = "Last name should be less then 20 character.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Email should be less then 50 character.")
    @Email(message = "Please enter valid email.")
    private String email;

    @NotBlank(message = "Mobile is required.")
    @Size(max = 15, message = "Mobile number should ne less then 15 character.")
    @Pattern(
            regexp = "^\\+?[1-9][0-9]{7,14}$",
            message = "Mobile must with start with country code."
    )
    private String mobile;

    @NotBlank(message = "Username is required.")
    @Size(max = 50, message = "Username should be less then 50 character.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 12, message = "Password should be more then 8 character and less then 12 character.")
    private String password;

    @NotBlank(message = "Role is required.")
    private UserRole role;
}
