package com.fajtech.auth.registration.token;

import com.fajtech.auth.User.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Fellipe Toledo
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    public Date expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
    }
}
