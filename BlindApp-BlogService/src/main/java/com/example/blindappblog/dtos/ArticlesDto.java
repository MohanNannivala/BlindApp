package com.example.blindappblog.dtos;


import lombok.Data;

import java.util.UUID;

@Data
public class ArticlesDto {
    private UUID articleId;
    private String title;
    private String content;
    private Integer numberOfLikes;
    private Integer numberOfComments;
}
