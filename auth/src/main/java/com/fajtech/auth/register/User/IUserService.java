package com.fajtech.auth.register.User;

import com.fajtech.auth.register.RegisterRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Fellipe Toledo
 */
public interface IUserService {

    List<User> getUsers();
    User registerUser(RegisterRequest request);
    Optional<User> findByEmail(String email);

}
