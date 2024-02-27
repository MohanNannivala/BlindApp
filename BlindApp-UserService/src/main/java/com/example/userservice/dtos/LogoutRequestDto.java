package com.example.userservice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class LogoutRequestDto {
    private UUID userId;
    private String token;
}