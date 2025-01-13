package com.auth.service.controller;

import com.auth.service.models.response.TokenResponse;
import com.auth.service.models.request.UserLoginRequest;
import com.auth.service.service.AuthService;
import com.auth.service.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthContoller {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private  AuthService authService;

    public AuthContoller(AuthenticationManager authenticationManager,
                         JWTUtils jwtUtils,
                         AuthService authService)
    {
        this.authenticationManager=authenticationManager;
        this.jwtUtils=jwtUtils;
        this.authService=authService;
    }
    @GetMapping("/validate")
    public ResponseEntity<TokenResponse> validate(HttpServletRequest request)
    {
       String token= jwtUtils.parseJwt(request);
       return ResponseEntity.ok(new TokenResponse(token,jwtUtils.validateJwtToken(token),jwtUtils.getUserNameFromJwtToken(token)));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody UserLoginRequest userLoginRequest)
    {
        TokenResponse tokenResponse=authService.authenticateUser(userLoginRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}
