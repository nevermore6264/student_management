package com.ute.security;

import java.util.Date;

import com.ute.entity.NguoiDung;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRET_KEY = "ufTpTQ4N18O8b7+Av2juOo+ekka1oKm0Y022PhDtv1muMGsV23u4UKLNSkJebOc3";
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 ngày

    public String generateToken(NguoiDung user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getVaiTros())
                .claim("userId", user.getMaNguoiDung())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }
}
