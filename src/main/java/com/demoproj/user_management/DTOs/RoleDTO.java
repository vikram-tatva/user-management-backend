package com.demoproj.user_management.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class RoleDTO {
    private String id;
    private String code;
    private String name;
}
