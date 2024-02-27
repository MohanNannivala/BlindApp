package com.example.blindappui.Dtos;


import lombok.Data;

import java.util.UUID;

@Data
public class CreateArticleDto {
    private UUID userId;
    private String title;
    private String content;
}
