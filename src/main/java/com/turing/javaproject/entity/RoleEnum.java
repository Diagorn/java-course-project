package com.turing.javaproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;
}
