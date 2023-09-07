package com.fajtech.auth.event.listener;

import com.fajtech.auth.event.RegistrationCompleteEvent;
import com.fajtech.auth.User.User;
import com.fajtech.auth.User.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Fellipe Toledo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private  User theUser;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //TODO

        // Get the newly registered user
        theUser = event.getUser();

        // create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken );

        // Build the verification URL tobe sent to the user
        String url = event.getApplicationUrl()+"/api/v1/register/verifyEmail?token="+verificationToken;

        // Send the e-mail

        log.info("Click the link to verify your registration : {}", url);

    }
}
