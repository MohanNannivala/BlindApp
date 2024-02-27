package com.example.blindappblog.models;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.UUID;

@Entity
@AttributeOverride(name = "uuid", column = @Column(name = "like_id"))
@Data
public class Likes extends BaseModel{
    private UUID userId;
    private String articleId;
}
