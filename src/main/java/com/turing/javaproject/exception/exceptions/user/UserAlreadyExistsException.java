package com.turing.javaproject.exception.exceptions.user;

import com.turing.javaproject.exception.exceptions.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
