package com.pragma.users.api.domain.model;

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
}
