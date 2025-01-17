package com.auth.service.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AuthEntryPointJwt
        implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        log.info(String.valueOf(response.getStatus()));
        log.info("Request Path:{}",request.getRequestURI());
        //log.info("authException:{}",authException.fillInStackTrace());
        if(response.getStatus() == 404) {
            response.getWriter().write("{\"error\": \"Resource Not Found !!\"," +
                    " \"status\": \"" + response.getStatus() + "\"}");
        }

        /*else {
            response.getWriter().write("{\"error\": \"UnAuthorized !!\", \"message\": \"" + authException.getMessage() + "\"}");
        }*/
    }
}
