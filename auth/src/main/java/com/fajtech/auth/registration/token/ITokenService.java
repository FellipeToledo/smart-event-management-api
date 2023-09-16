package com.fajtech.auth.registration.token;

import com.fajtech.auth.User.User;

/**
 * @author Fellipe Toledo
 */
public interface ITokenService {

    void saveUserVerificationToken(User theUser, String verificationToken);
    String validateToken(String theToken);
    VerificationToken generateNewVerificationToken(String oldToken);


}
