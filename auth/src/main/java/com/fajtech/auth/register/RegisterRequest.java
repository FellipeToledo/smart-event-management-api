package com.fajtech.auth.register;

import com.fajtech.auth.register.validation.password.PasswordMatching;
import com.fajtech.auth.register.validation.password.StrongPassword;
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
public record RegisterRequest(
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
