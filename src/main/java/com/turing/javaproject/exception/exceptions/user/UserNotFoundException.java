package com.turing.javaproject.exception.exceptions.user;

import com.turing.javaproject.exception.exceptions.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
