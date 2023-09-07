package com.fajtech.auth.registration;

import com.fajtech.auth.User.User;
import com.fajtech.auth.User.UserService;
import com.fajtech.auth.event.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;

/**
 * @author Fellipe Toledo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public String registerUser(RegistrationRequest registrationRequest, final HttpServletRequest request) {
        User user = userService.registerUser(registrationRequest);
        // publish registration event

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please check your email for to complete your registration";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +request.getServerName()+ ":" +request.getServerPort()+request.getContextPath();
    }
}
