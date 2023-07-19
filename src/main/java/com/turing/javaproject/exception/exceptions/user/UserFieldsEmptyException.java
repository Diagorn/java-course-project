package com.turing.javaproject.exception.exceptions.user;

public class UserFieldsEmptyException extends RuntimeException {
    public UserFieldsEmptyException(String message) {
        super(message);
    }
}
