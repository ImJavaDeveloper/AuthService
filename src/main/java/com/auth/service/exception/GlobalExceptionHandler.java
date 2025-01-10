package com.auth.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ErrorDetails> handleInvalidCredentialException(InvalidCredentialException ex, WebRequest req)
    {
        ErrorDetails errorDetails=new ErrorDetails(
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage(),
                LocalDateTime.now(),
                req.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorDetails> handleUnAuthorizedException(UnAuthorizedException ex, HttpServletRequest req)
    {
        ErrorDetails errorDetails=new ErrorDetails(
                HttpStatus.UNAUTHORIZED.toString(),
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI());
        return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
    }

}
