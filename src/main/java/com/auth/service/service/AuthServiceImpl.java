package com.auth.service.service;

import com.auth.service.exception.InvalidCredentialException;
import com.auth.service.models.response.TokenResponse;
import com.auth.service.models.request.UserLoginRequest;
import com.auth.service.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    AuthServiceImpl(JWTUtils jwtUtils, AuthenticationManager authenticationManager)
    {
        this.authenticationManager=authenticationManager;
        this.jwtUtils=jwtUtils;
    }

    @Override
    public TokenResponse authenticateUser(UserLoginRequest userLoginRequest) {
        Authentication authentication = null;
        String jwtToken=null;
        try
        {
            authentication=authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    userLoginRequest.getUsername(),userLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwtToken=jwtUtils.generateJWTToken(authentication);
        }
        catch (Exception ex)
        {
            if(ex instanceof BadCredentialsException)
            {

                log.info("Getting BadCredentialsException Error While Validating User:{}",userLoginRequest.getUsername());
                throw new InvalidCredentialException("Invalid Credential");
            }
            log.error("Getting exception while authenticating user:{}",ex);
            return new TokenResponse(null,false, userLoginRequest.getUsername());
        }
        

        return new TokenResponse(jwtToken,true, userLoginRequest.getUsername());
    }
}
