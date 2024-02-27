package com.example.blindappblog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AttributeOverride(name = "uuid", column = @Column(name = "favorite_id"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorites extends BaseModel{
    private UUID userId;
    private UUID articleId;
}
