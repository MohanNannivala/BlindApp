package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "uuid", column = @Column(name = "user_id"))
})
@Data
public class Users extends BaseModel{
    @Column(unique=true)
    String username;
    String password;
}
