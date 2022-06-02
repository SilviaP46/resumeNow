package com.app.demo.error.exception;

import java.time.LocalDateTime;

public class ApiError {

    private final LocalDateTime timestamp;
    private final ErrorCodes errorCode;
    private final String message;

    public ApiError(LocalDateTime timestamp, ErrorCodes errorCode, String message) {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.message = message;

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
