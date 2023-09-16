package com.fajtech.auth.User;

import com.fajtech.auth.exception.UserAlreadyExistsException;
import com.fajtech.auth.registration.RegistrationRequest;
import com.fajtech.auth.registration.token.VerificationToken;
import com.fajtech.auth.registration.token.VerificationTokenRepository;

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
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
       Optional<User> user = this.findByEmail((request.email()));
       if (user.isPresent()){
           throw new UserAlreadyExistsException(
                   "User with e-mail "+request.email() + "already exists");
       }
       var newUser = new User();
       newUser.setFirstName(request.firstName());
       newUser.setLastName(request.lastName());
       newUser.setEmail(request.email());
       newUser.setPassword(passwordEncoder.encode(request.password()));
       newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
