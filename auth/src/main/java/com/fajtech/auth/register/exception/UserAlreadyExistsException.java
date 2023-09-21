package com.fajtech.auth.register.exception;

/**
 * @author Fellipe Toledo
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
