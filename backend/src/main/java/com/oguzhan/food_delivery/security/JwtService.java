package com.oguzhan.food_delivery.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails) {

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        List< String> role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .signWith(getSignKey(SECRET_KEY))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .claim("role", role)
                .compact();
    }

    private SecretKey getSignKey(String SECRET_KEY) {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<String> getRoleFromToken(String token) {
        Claims payload = Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return payload.get("role", List.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));
    }
}

