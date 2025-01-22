package com.auth.service.controller;

import com.auth.service.models.response.TokenResponse;
import com.auth.service.models.request.UserLoginRequest;
import com.auth.service.repository.RoleRepository;
import com.auth.service.repository.UserCredRepository;
import com.auth.service.service.AuthService;
import com.auth.service.service.UserProfileServiceImpl;
import com.auth.service.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name="AuthService", description="End points for Login service")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private  AuthService authService;
    private UserProfileServiceImpl userProfileService;
    @Autowired
    private UserCredRepository userCredRepository;
    @Autowired
    private RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JWTUtils jwtUtils,
                          AuthService authService)
    {
        this.authenticationManager=authenticationManager;
        this.jwtUtils=jwtUtils;
        this.authService=authService;
    }

    @Operation(
            summary = "Validate JWT Token ",
            description = "JWT Token Validation ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token Validated")
    })
    @GetMapping("/validate")
    public ResponseEntity<TokenResponse> validate(HttpServletRequest request)
    {
       TokenResponse tokenResponse = null;
       String token= jwtUtils.parseJwt(request);
       try{
           boolean isTokenValidated=jwtUtils.validateJwtToken(token);
           String username=jwtUtils.getUserNameFromJwtToken(token);
           log.info(String.valueOf(isTokenValidated));
           tokenResponse=new TokenResponse(token,isTokenValidated,username);

       }catch (Exception e)
       {
           log.info("Exception Occurred in controller:{}");
           tokenResponse=new TokenResponse(token,false,null);
       }

       return ResponseEntity.ok(tokenResponse);
    }
    @Operation(
            summary = "Login With Valid Credential",
            description = "User should be able to login with valid credential and it should return valid JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful Login")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginRequest userLoginRequest)
    {
        TokenResponse tokenResponse=authService.authenticateUser(userLoginRequest);
        if(tokenResponse.isAuthenticated())
        return ResponseEntity.ok(tokenResponse);
        else
            return new ResponseEntity<>("Invalid User",HttpStatus.UNAUTHORIZED);
    }

}
