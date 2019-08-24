package ru.smartel.strike.security;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import ru.smartel.strike.model.User;


/**
 * Адаптер для связи FirebaseToken из библиотеки от google со Spring Security.
 * Служит для представления токена
 */
public class FirebaseTokenAdapter extends AbstractAuthenticationToken {

    private String bearer;
    private FirebaseToken credentials;
    private User principal;

    public FirebaseTokenAdapter(String bearer) {
        super(null);
        this.bearer = bearer;
    }

    public void setCredentials(FirebaseToken firebaseToken) {
        credentials = firebaseToken;
    }

    @Override
    public FirebaseToken getCredentials() {
        return credentials;
    }

    public void setPrincipal(User user) {
        principal = user;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
