package com.demoproj.user_management.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum UserRole {
    ADMIN(Set.of(Permissions.USER_CREATE, Permissions.USER_READ, Permissions.USER_UPDATE, Permissions.USER_DELETE)),
    USER(Set.of(Permissions.USER_READ, Permissions.USER_UPDATE));

    private final Set<Permissions> permissions;

    UserRole(Set<Permissions> userPermissions) {
       this.permissions = userPermissions;
    }
}
