package com.auth.service.service;

import com.auth.service.models.response.TokenResponse;
import com.auth.service.models.request.UserLoginRequest;

public interface AuthService {

    TokenResponse authenticateUser(UserLoginRequest userLoginRequest);
}
