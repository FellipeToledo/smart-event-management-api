package com.fajtech.auth.register.User;

import com.fajtech.auth.register.exception.UserAlreadyExistsException;
import com.fajtech.auth.register.RegisterRequest;
import com.fajtech.auth.register.validation.token.TokenRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Fellipe Toledo
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegisterRequest request) {
       Optional<User> user = this.findByEmail((request.email()));
       if (user.isPresent()){
           throw new UserAlreadyExistsException(
                   "User with e-mail "+request.email() + "already exists");
       }
       var newUser = new User();
       newUser.setUserName(request.userName());
       newUser.setEmail(request.email());
       newUser.setPassword(passwordEncoder.encode(request.password()));
       newUser.setConfirmPassword(passwordEncoder.encode(request.confirmPassword()));
       newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
