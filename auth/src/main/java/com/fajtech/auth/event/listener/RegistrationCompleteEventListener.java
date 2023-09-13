package com.fajtech.auth.event.listener;

import com.fajtech.auth.event.RegistrationCompleteEvent;
import com.fajtech.auth.User.User;
import com.fajtech.auth.User.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Fellipe Toledo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;
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
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration : {}", url);
    }
    public void sendVerificationEmail (String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Smart Event Management";
        String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us, "+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Smart Event Management";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("smarteventmanagement@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
