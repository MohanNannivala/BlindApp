package com.example.userservice.exceptions;

import com.example.userservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(WrongPasswordException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(UserNotAuthorizedException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

}