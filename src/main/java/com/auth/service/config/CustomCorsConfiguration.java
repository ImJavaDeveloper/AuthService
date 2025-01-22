package com.auth.service.config;

import com.auth.service.utils.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(AppConstant.allowedOrigins);
        corsConfiguration.setAllowedHeaders(AppConstant.allowedHeaders);
        corsConfiguration.setExposedHeaders(AppConstant.allowedExposedHeaders);
        corsConfiguration.setAllowedMethods(AppConstant.allowedMethods);
        return corsConfiguration;
    }
}