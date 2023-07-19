package com.turing.javaproject.exception.exceptions.common;

import org.springframework.http.HttpStatus;

public class WrongDateException extends BaseException {
    public WrongDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
