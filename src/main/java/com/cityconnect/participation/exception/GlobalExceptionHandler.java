package com.cityconnect.participation.exception;


import com.cityconnect.participation.web.dto.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(AlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleAlreadyRegistered(AlreadyRegisteredException exception){
        log.warn("Rejected duplicate registration: {}", exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.value(), exception.getMessage());

    }


    @ExceptionHandler(RegistrationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RegistrationNotFoundException exception){
        log.warn("Registration not found: {}", exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.value(),exception.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" "));
        log.warn("Invalid request: {}", message);
        return new ApiError(HttpStatus.BAD_REQUEST.value(), message);
    }


    public ApiError handleUnreadableBody(HttpMessageNotReadableException exception){
        log.warn("Unreadable request body: {}", exception.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "The request body is missing or malformed.");

    }







}
