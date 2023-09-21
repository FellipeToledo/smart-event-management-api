package com.fajtech.auth.register.validation.token;

import com.fajtech.auth.register.User.User;

import java.util.Date;

/**
 * @author Fellipe Toledo
 */
public interface ITokenService {

    void saveUserVerificationToken(User theUser, String verificationToken);
    String validateToken(String theToken);
    VerificationToken generateNewVerificationToken(String oldToken);

    Date getTokenExpirationTime();


}
