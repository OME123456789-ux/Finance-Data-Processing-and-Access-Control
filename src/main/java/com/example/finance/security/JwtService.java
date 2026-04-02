package com.example.finance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final JwtProperties properties;
  private final Key signingKey;

  public JwtService(JwtProperties properties) {
    this.properties = properties;
    this.signingKey = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(CustomUserDetails userDetails) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + properties.getExpirationMs());

    Map<String, Object> claims = Map.of(
        "userId", userDetails.getId(),
        "role", userDetails.getRole().name()
    );

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getEmail())
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractEmail(String token) {
    return parseClaims(token).getSubject();
  }

  public boolean isTokenValid(String token, CustomUserDetails userDetails) {
    Claims claims = parseClaims(token);
    String email = claims.getSubject();
    Date exp = claims.getExpiration();
    return email != null && email.equals(userDetails.getEmail()) && exp != null && exp.after(new Date());
  }

  private Claims parseClaims(String token) {
    try {
      // Your IDE currently resolves a jjwt API where `parseClaimsJws(...)` is only
      // available on the built parser, not directly on the parser builder.
      return Jwts.parser()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException ex) {
      throw ex;
    }
  }
}

