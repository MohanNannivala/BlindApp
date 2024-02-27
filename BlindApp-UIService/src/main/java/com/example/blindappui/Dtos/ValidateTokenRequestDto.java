package com.example.blindappui.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenRequestDto {
    private String token;
    private UUID userId;
}
