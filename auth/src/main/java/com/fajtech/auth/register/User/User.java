package com.fajtech.auth.register.User;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.NaturalId;

/**
 * @author Fellipe Toledo
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private boolean isEnabled = false;

}
