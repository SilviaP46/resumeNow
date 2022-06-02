package com.app.demo.error.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String s) {
        super(s);
    }
}
