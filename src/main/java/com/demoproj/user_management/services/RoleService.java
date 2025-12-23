package com.demoproj.user_management.services;

import com.demoproj.user_management.DTOs.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO role);
    RoleDTO updateRole(String roleId, RoleDTO role);
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(String id);
    void deleteRoleById(String id);
}
