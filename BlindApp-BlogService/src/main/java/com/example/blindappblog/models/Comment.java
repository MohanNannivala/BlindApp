package com.example.blindappblog.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@AttributeOverride(name = "uuid", column = @Column(name = "comment_id"))
@Data
public class Comment extends BaseModel{
    private String userId;
    private String comment;
}
