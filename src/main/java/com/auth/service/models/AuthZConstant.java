package com.auth.service.models;

import java.util.List;

public class AuthZConstant
{
    public final static List<String> allowedURI=List.of(
            "/api/v1/login",
            "/api/v1/validate",
            "/actuator");
}
