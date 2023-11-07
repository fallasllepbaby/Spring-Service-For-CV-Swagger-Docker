package com.example.springserviceforcvswaggerdocker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage alreadyExistException(AlreadyExistException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return message;
    }
}
