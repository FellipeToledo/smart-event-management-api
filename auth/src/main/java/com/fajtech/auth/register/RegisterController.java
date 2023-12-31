package com.fajtech.auth.register;

import com.fajtech.auth.register.User.User;
import com.fajtech.auth.register.User.UserService;
import com.fajtech.auth.register.validation.email.event.RegistrationCompleteEvent;
import com.fajtech.auth.register.validation.email.event.listener.RegistrationCompleteEventListener;
import com.fajtech.auth.register.validation.token.TokenService;
import com.fajtech.auth.register.validation.token.VerificationToken;
import com.fajtech.auth.register.validation.token.TokenRepository;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author Fellipe Toledo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegisterController {
    private final TokenService tokenService;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final TokenRepository tokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;

    @PostMapping
    public String registerUser(@Valid @RequestBody RegisterRequest registerRequest, final HttpServletRequest request) {
        User user = userService.registerUser(registerRequest);

        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please check your email for to complete your registration";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        String url = applicationUrl(servletRequest)+"/api/v1/register/resend-verification-email?token="+token;

        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = tokenService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification email, <a href=\"" +url+"\"> Get a new verification link. </a>";
    }

    @GetMapping("/resend-verification-email")
    public String resendVerificationAccount(@RequestParam("token") String oldToken, final HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = tokenService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationAccountEmail(theUser, applicationUrl(servletRequest), verificationToken);
        return "A new verification link hs been sent to your email," +
                " please, check to active your account";
    }

    private void resendVerificationAccountEmail(User theUser, String applicationUrl, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/register/verifyEmail?token=" +verificationToken.getToken();
        eventListener.sendVerificationEmail(url);

        log.info("Click the link to verify your registration : {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +request.getServerName()+ ":" +request.getServerPort()+request.getContextPath();
    }
}
