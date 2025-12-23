package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.RoleDTO;
import com.demoproj.user_management.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.createRole(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String id, @RequestBody RoleDTO role) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.updateRole(id, role));
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO>  getRoleById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.getRoleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable String id) {
        roleService.deleteRoleById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
