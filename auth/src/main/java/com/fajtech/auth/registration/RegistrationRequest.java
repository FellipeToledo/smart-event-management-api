package com.fajtech.auth.registration;

/**
 * @author Fellipe Toledo
 */
public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {
}
