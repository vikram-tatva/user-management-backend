package com.demoproj.user_management.services.impl;

import com.demoproj.user_management.DTOs.RoleDTO;
import com.demoproj.user_management.entities.Role;
import com.demoproj.user_management.exceptions.ResourceNotFoundException;
import com.demoproj.user_management.repositories.RoleRepository;
import com.demoproj.user_management.services.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper  modelMapper;

    @Override
    public RoleDTO createRole(RoleDTO role) {
        Role roleEntity = modelMapper.map(role, Role.class);
        roleEntity.setId(UUID.randomUUID().toString());
        return modelMapper.map(roleRepository.save(roleEntity), RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(String roleId, RoleDTO role) {
        Role roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("User role not found by id: " + roleId));
        modelMapper.map(roleEntity, role);
        roleRepository.save(roleEntity);

        return modelMapper.map(roleEntity, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roleEntities = roleRepository.findAll();
        return roleEntities
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User role not found by id: " + id));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public void deleteRoleById(String id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("User role not found by id: " + id);
        }

        roleRepository.deleteById(id);
    }
}
