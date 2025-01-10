package com.auth.service.utils;

import com.auth.service.service.UserDetailsImp;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTUtils {

    @Value("${app.jwtSecret}")
    private String JWTSecret;
    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMS;

    public String generateJWTToken(Authentication authentication)
    {
        UserDetailsImp userDetailsImp= (UserDetailsImp) authentication.getPrincipal();
        List<String> listOfRoles=userDetailsImp.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Map map=new HashMap();
        map.put("roles",listOfRoles);
        return Jwts.builder()
                .setClaims(map)
                .setSubject(userDetailsImp.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMS))
                .signWith(SignatureAlgorithm.HS256,JWTSecret)
                .compact();

    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(JWTSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWTSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
