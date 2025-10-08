package com.tamara.BankingAppSpringBoot.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();   // Get the logged-in user's email/username
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationMs);  // Expiry time

        return Jwts.builder()
                .setSubject(username)     // The "owner" of the token
                .setIssuedAt(currentDate) // When it was created
                .setExpiration(expireDate) // When it will expire
                .signWith(Key())          // Signing with your secret key
                .compact();               // Convert to a string (the actual token)
    }


//    Converts Base64 secret key into a cryptographic key that JJWT (the JWT library) understands.

    private Key Key() {
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public  String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return  claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Key())
                    .build()
                    .parse(token);
            return  true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
