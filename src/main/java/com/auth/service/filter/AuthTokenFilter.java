package com.auth.service.filter;

import com.auth.service.exception.UnAuthorizedException;
import com.auth.service.models.AuthZConstant;
import com.auth.service.service.UserCredService;
import com.auth.service.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private JWTUtils jwtUtils;
    private UserCredService userCredService;

    public AuthTokenFilter(JWTUtils jwtUtils,UserCredService userCredService)
    {
     this.jwtUtils=jwtUtils;
     this.userCredService=userCredService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken=jwtUtils.parseJwt(request);
        if(AuthZConstant.allowedURI.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            if(jwtToken != null && jwtUtils.validateJwtToken(jwtToken))
            {
                String username=jwtUtils.getUserNameFromJwtToken(jwtToken);
                UserDetails userDetails= userCredService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex)
        {
            log.error("Cannot set user authentication: {}", ex);
        }
        filterChain.doFilter(request, response);
    }
}
