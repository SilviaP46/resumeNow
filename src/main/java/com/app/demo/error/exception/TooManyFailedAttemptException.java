package com.app.demo.error.exception;

public class TooManyFailedAttemptException extends RuntimeException {
    public TooManyFailedAttemptException(String s) {
        super(s);
    }
}
