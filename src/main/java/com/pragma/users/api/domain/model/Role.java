package com.pragma.users.api.domain.model;

import java.util.Arrays;
import java.util.Objects;

public enum Role {
    ROLE_ADMIN(1L),
    ROLE_OWNER(2L),
    ROLE_EMPLOYEE(3L),
    ROLE_CLIENT(4L);

    private Long id;

    Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static String getRoleName(Long idRole) {
        return Arrays.stream(Role.values())
                .filter(role -> Objects.equals(role.getId(), idRole)).findFirst().orElseThrow().name();
    }
}
