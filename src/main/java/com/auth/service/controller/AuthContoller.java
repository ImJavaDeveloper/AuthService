package com.auth.service.controller;

import com.auth.service.entity.Role;
import com.auth.service.entity.UserCredential;
import com.auth.service.models.response.TokenResponse;
import com.auth.service.models.request.UserLoginRequest;
import com.auth.service.repository.RoleRepository;
import com.auth.service.repository.UserCredRepository;
import com.auth.service.service.AuthService;
import com.auth.service.service.UserProfileServiceImpl;
import com.auth.service.utils.JWTUtils;
import com.netflix.discovery.converters.Auto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name="Note", description="End points for note service")
public class AuthContoller {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private  AuthService authService;
    private UserProfileServiceImpl userProfileService;
    @Autowired
    private UserCredRepository userCredRepository;
    @Autowired
    private RoleRepository roleRepository;

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
            summary = "Fetch all Notes",
            description = "fetches all Notes entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody UserLoginRequest userLoginRequest)
    {
        TokenResponse tokenResponse=authService.authenticateUser(userLoginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<UserLoginRequest> create(@RequestBody UserLoginRequest userLoginRequest)
    {
        UserCredential userCredential=new UserCredential();
        userCredential.setUsername(userLoginRequest.getUsername());
        userCredential.setPassword(userLoginRequest.getPassword());
        Role role=roleRepository.findByRoleId(2);
        log.info(role.toString());
        Set<Role> roles=new HashSet<>();
        roles.add(role);
        userCredential.setRoles(roles);
        userCredRepository.save(userCredential);
        return ResponseEntity.ok(userLoginRequest);
    }
}
