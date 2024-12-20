package com.palla.Insta_Clone.Security;

import com.palla.Insta_Clone.Config.SecurityContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtTokenProvider {
    @Autowired
    SecurityContext securityContext;

    public JwtTokenClaims getClaimsFromToken(String token){
        SecretKey key= Keys.hmacShaKeyFor(securityContext.getJWT_KEY().getBytes());
        Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String username=String.valueOf(claims.get("username"));
        JwtTokenClaims jwtTokenClaims=new JwtTokenClaims();
        jwtTokenClaims.setUsername(username);
        return jwtTokenClaims;
    }
}
