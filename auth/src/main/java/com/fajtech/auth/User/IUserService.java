package com.fajtech.auth.User;

import com.fajtech.auth.registration.RegistrationRequest;
import com.fajtech.auth.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

/**
 * @author Fellipe Toledo
 */
public interface IUserService {

    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);

}
