package com.auth.service.config;

import com.auth.service.filter.AppAccessDeniedHandler;
import com.auth.service.filter.AuthEntryPointJwt;
import com.auth.service.filter.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private AuthTokenFilter authTokenFilter;
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPoint unAuthorizedHandler;
    @Autowired
    private AppAccessDeniedHandler appAccessDeniedHandler;
    @Autowired
    private CustomCorsConfiguration customCorsConfiguration;

    public SecurityConfig(AuthTokenFilter authTokenFilter, UserDetailsService userDetailsService)
    {
        this.authTokenFilter=authTokenFilter;
        this.userDetailsService=userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
           // .cors(cors -> cors.configurationSource(customCorsConfiguration))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request-> request
                .requestMatchers("/api/v1/login",
                        "/api/v1/validate","/actuator/**",
                        "/v3/api-docs","/favicon.ico",
                        "/swagger-ui/**","/swagger-ui.html","/v3/api-docs/swagger-config","/v3/api-docs.yaml"
                ).permitAll())
            .authorizeHttpRequests(request->request
                    .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex->ex
                    .authenticationEntryPoint(unAuthorizedHandler)
                    .accessDeniedHandler(appAccessDeniedHandler)
            )
            .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
