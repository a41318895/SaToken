package com.akichou.satokentest.global.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {

        super(message) ;
    }
}
