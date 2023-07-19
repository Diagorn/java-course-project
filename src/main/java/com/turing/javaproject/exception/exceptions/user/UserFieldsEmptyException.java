package com.turing.javaproject.exception.exceptions.user;

import com.turing.javaproject.exception.exceptions.common.BaseException;
import org.springframework.http.HttpStatus;

public class UserFieldsEmptyException extends BaseException {
    public UserFieldsEmptyException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
