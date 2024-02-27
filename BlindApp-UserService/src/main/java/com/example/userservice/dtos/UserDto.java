package com.example.userservice.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
}
