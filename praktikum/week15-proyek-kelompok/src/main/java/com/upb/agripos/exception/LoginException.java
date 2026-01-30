package com.upb.agripos.exception;

/**
 * Custom exception for authentication failures.
 * Thrown when login credentials are invalid or authentication process fails.
 */
public class LoginException extends Exception {
    
    public LoginException(String message) {
        super(message);
    }
    
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
