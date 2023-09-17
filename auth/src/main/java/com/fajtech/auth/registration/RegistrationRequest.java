package com.fajtech.auth.registration;

import com.fajtech.auth.registration.validation.PasswordMatching;
import com.fajtech.auth.registration.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Fellipe Toledo
 */
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)
public record RegistrationRequest(
        @NotBlank
        @Size(min = 3, max = 20)
        String userName,
        @NotBlank
        @Size(max = 50)
        @Email
        String email,
        @StrongPassword
        String password,
        String confirmPassword,
        String role) {
}
