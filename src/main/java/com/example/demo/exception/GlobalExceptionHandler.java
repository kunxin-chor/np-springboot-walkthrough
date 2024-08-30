package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class, JwtException.class })
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Unauthorized: Invalid or missing JWT");
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    // You can add more exception handlers here for other types of exceptions
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error"); // This will render error.html template
        return modelAndView;
    }
}