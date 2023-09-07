package com.fajtech.auth.event.listener;

import com.fajtech.auth.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Fellipe Toledo
 */
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //TODO
        // Get the newly registered user
        // create a verification token for the user
        // save the verification token for the user
        // Build the verification URL tobe sent to the user
        // Send the e-mail

    }
}
