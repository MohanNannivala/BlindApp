package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "uuid", column = @Column(name = "session_id"))
})
public class Session extends BaseModel{
    public static Object SessionStatus;
    private String token;
    private Date expiresAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}