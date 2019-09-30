package ru.smartel.strike.security.token;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.smartel.strike.entity.User;

import java.util.Collection;
import java.util.Set;

public class UserAuthenticationToken implements Authentication {

    public UserAuthenticationToken(User user, Set<GrantedAuthority> authorities, FirebaseToken credentials, boolean authenticated) {
        this.user = user;
        this.authorities = authorities;
        this.credentials = credentials;
        this.authenticated = authenticated;
    }

    private User user;
    private Set<GrantedAuthority> authorities;
    private FirebaseToken credentials;
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
