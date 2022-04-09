package com.app.demo.error.exception;

public class UserPasswordIncorrectException extends RuntimeException{
    public UserPasswordIncorrectException(String s) {
        super(s);
    }
}
