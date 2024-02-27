package com.example.userservice.services;

import com.example.userservice.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey testKey = Jwts.SIG.HS256.key().build();

    public String generateToken(Users users){

        Map<String, Object> message = new HashMap<>();
        message.put("username", users.getUsername());
        message.put("createdAt", new Date());
        message.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String jws = Jwts.builder().signWith(testKey)
                .claims(message)// #1
                .compact();

        return jws;

    }

    public Map<String, Object> verifyToken(String token) {

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(testKey)
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload();
    }
}
