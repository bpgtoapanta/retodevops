package com.retotcs.demodevops.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Service
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Método para generar el token
    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, subject);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutos de expiración
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, String subject) {
      try {
          final String username = extractUsername(token);
          return (username.equals(subject) && !isTokenExpired(token));
      } catch (io.jsonwebtoken.security.SignatureException e) {
          // Firma JWT no coincide
          System.out.println("Error: Firma JWT no válida. " + e.getMessage());
          return false;
      } catch (MalformedJwtException e) {
          // Token mal formado
          System.out.println("Error: El JWT está mal formado. " + e.getMessage());
          return false;
      } catch (ExpiredJwtException e) {
          // Token ha expirado
          System.out.println("Error: El JWT ha expirado. " + e.getMessage());
          return false;
      } catch (UnsupportedJwtException e) {
          // Token no soportado
          System.out.println("Error: El JWT no es soportado. " + e.getMessage());
          return false;
      } catch (IllegalArgumentException e) {
          // Token vacío o nulo
          System.out.println("Error: El JWT es inválido (claims vacíos o nulos). " + e.getMessage());
          return false;
      }
  }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
      return Jwts.parserBuilder() 
              .setSigningKey(SECRET_KEY) 
              .build() 
              .parseClaimsJws(token) 
              .getBody().getExpiration();
  }

  // Extraer el nombre de usuario (o sujeto)
  private String extractUsername(String token) {
      return Jwts.parserBuilder() 
              .setSigningKey(SECRET_KEY) 
              .build() 
              .parseClaimsJws(token) 
              .getBody().getSubject();
  }
}
