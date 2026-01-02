package com.demoproj.user_management.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum UserRole {
    ADMIN(Set.of(Permissions.CREATE, Permissions.READ, Permissions.UPDATE, Permissions.DELETE)),
    USER(Set.of(Permissions.READ, Permissions.UPDATE));

    private final Set<Permissions> permissions;

    UserRole(Set<Permissions> userPermissions) {
       this.permissions = userPermissions;
    }
}
