package com.jitsi.jitsi_backend.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key key = Keys.hmacShaKeyFor("MyVerySecretKeyThatIsAtLeast32Chars!".getBytes());

    public String generateToken(String userName, String roomId) {
        long now = System.currentTimeMillis();
        Date expiry = new Date(now + 3600_000); // 1 saat geçerli

        return Jwts.builder()
                .setIssuer("my-app")
                .setSubject("jitsi.local")
                .setAudience("jitsi")
                .claim("room", roomId)
                .claim("context", Map.of("user", Map.of("id", userName, "name", userName)))
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
