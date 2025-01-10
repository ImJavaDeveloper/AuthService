package com.auth.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UnAuthorizedException extends RuntimeException{

    public UnAuthorizedException()
    {
        super();
    }
    public UnAuthorizedException(String message, Throwable ex)
    {
        super(message,ex);
    }
    public UnAuthorizedException(String message)
    {
        super(message);
    }
}
