package com.app.demo.error.exception;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException(String msg){
        super(msg);
    }
}
