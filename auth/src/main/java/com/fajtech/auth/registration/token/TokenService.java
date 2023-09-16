package com.fajtech.auth.registration.token;

import com.fajtech.auth.User.User;
import com.fajtech.auth.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * @author Fellipe Toledo
 */
@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            return "Verification link already expired," +
                    " Please, click the link below to receive a new verification link";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        VerificationToken verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }
}
