package com.example.userservice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
