package com.app.demo.error.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserPasswordIncorrectException.class })
    protected ResponseEntity<Object> handleUsernamePasswordIncorrectException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(createApiErrorCurrentTime(ErrorCodes.USERNAME_PASSWORD_INCORRECT, exception.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    protected ResponseEntity<Object> handleUsernameAlreadyExistsException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(createApiErrorCurrentTime(ErrorCodes.USERNAME_ALREADY_EXISTS, exception.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    private ApiError createApiErrorCurrentTime(ErrorCodes code, String msg){
        return new ApiError(LocalDateTime.now(), code, msg);
    }
}
