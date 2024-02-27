package com.example.blindappblog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AttributeOverride(name = "uuid", column = @Column(name = "article_id"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articles extends BaseModel{
    public Articles(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    private UUID userId;
    private String title;
    private String content;

    @OneToMany
    @JoinColumn(name = "article_id")
    private List<Comment> comments;
}
