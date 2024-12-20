package com.palla.Insta_Clone.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    SecurityContext securityContext;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader(SecurityContext.HEADER);
        if(jwt!=null){
            try {
                jwt=jwt.substring(7);
                SecretKey key= Keys.hmacShaKeyFor(securityContext.getJWT_KEY().getBytes());
                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String username=String.valueOf(claims.get("username"));
                String authorities=(String)claims.get("authorities");
                List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth=new UsernamePasswordAuthenticationToken(username,null,auths);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (Exception e){
                throw new BadCredentialsException("Invalid token");

            }
        }

        filterChain.doFilter(request,response);
    }

    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException{
        return req.getServletPath().equals("/signin");
    }
}
