package com.example.userservice.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionDto {

    private HttpStatus httpStatus;
    private String message;
    public ExceptionDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
