package ru.smartel.strike.security;

import org.springframework.security.core.AuthenticationException;

public class FirebaseAuthenticationException extends AuthenticationException {
    public FirebaseAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public FirebaseAuthenticationException(String msg) {
        super(msg);
    }
}
