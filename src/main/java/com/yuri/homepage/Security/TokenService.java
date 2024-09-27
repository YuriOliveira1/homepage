package com.yuri.homepage.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yuri.homepage.entities.User;

@Service
public class TokenService {

    @Value("${api.security.token.security}")
    private String secret;

    // Method for generation Token
    // Use a algorithm for generate(HMAC256)
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getUsername())
                .withExpiresAt(expirationDate())
                .sign(algorithm);
                return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error token");
        }
    }

    // Method to validate Token
    // Return a empty String for JWT verificate
    // if the token is valid or pass the expiration time
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
    
    // Return a Instant
    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
