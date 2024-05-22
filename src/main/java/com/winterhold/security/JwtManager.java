package com.winterhold.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtManager {
    private final String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";

    public String generateToken(String username, String secretKey, String subject, String audience){
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(subject)
                .setIssuer("http://localhost:8704")
                .setAudience(audience)
                .claim("username", username)  //claim: menambah payload
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return builder.compact();
    }

    public  Boolean validateToken(String token, UserDetails userDetails){
        var username = getUsernameByToken(token);
        Boolean isMatch = username.equals(userDetails.getUsername());
        return true;
    }

    public String getUsernameByToken(String token){
        Claims claims = getClaimsFromToken(token);
        String username = claims.get("username", String.class);

        return username;
    }



    private Claims getClaimsFromToken(String token){
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);
        Claims claims = signatureAndClaims.getBody();

        return claims;
    }
}
