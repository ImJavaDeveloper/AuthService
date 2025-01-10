package com.auth.service.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidCredentialException extends BadCredentialsException {

    public InvalidCredentialException(String msg) {
        super(msg);
    }

    public InvalidCredentialException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
