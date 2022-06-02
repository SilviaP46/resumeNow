package com.app.demo.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCodes {
    USERNAME_ALREADY_EXISTS(0, "Username already exists."),
    USERNAME_PASSWORD_INCORRECT(1, "Username or password are incorrect.");

    private final int code;
    private final String description;

    ErrorCodes(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Error" + code + ": " + description;
    }

}
