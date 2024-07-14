package com.bandeira.users.model;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("user"),

    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
