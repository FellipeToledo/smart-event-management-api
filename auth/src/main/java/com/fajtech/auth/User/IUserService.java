package com.fajtech.auth.User;

import com.fajtech.auth.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Fellipe Toledo
 */
public interface IUserService {

    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);
}
