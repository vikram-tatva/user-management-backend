package com.demoproj.user_management.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET_KEY = "thisismysecretkeyforgeneratingjwttokern+key";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Autowired
    private TokenUtil tokenUtil;

    public String generateToken(String username){
        return  Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + tokenUtil.getAccess().getExpire_in_ms()))
                        .signWith(secretKey, SignatureAlgorithm.HS256)
                        .compact();
    }

    private Claims extractClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    private boolean isTokenExpired(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String username, UserDetails userDetails, String token){
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}
