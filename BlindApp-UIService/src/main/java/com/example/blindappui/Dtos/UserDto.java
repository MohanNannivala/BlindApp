package com.example.blindappui.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
}
