package com.ceuma.connectfono.services;

import com.ceuma.connectfono.core.dto.TokenDto;
import com.ceuma.connectfono.core.models.Staff;
import com.ceuma.connectfono.core.patient.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Service
public class JsonWebTokenService {
    // mover isso pra um env depois
    @Value("${jwt.secret:flamengo}")
    private String secretKey;

    @Autowired
    StaffService staffService;

    public TokenDto generateToken(String username, String password) {
        Staff staff = staffService.login(username, password);
        String token = Jwts.builder()
                .subject(staff.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return new TokenDto(token, staff);
    }

    /**
     * Validates the given JWT and returns its subject (usually username / name).
     * Throws BadRequestException on invalid/expired tokens.
     */
    public String validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new BadRequestException("Token inválido ou ausente");
        }

        token = token.trim();
        if (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(7).trim();
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                throw new BadRequestException("Token expirado");
            }

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("Token expirado");
        } catch (JwtException e) {
            // Covers signature and other parsing exceptions
            throw new BadRequestException("Token inválido");
        } catch (Exception e) {
            throw new BadRequestException("Erro ao validar token");
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        // HS256 requires a key of at least 256 bits (32 bytes). Pad if needed.
        if (keyBytes.length < 32) {
            keyBytes = Arrays.copyOf(keyBytes, 32);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
