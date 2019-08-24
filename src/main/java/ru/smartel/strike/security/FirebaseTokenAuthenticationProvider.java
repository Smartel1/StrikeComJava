package ru.smartel.strike.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.smartel.strike.exception.UnauthtenticatedException;
import ru.smartel.strike.model.User;
import ru.smartel.strike.repository.UserRepository;

/**
 * Проверяет токен от firebase на валидность с помощью библиотеки от google.
 * При успешной проверке заполняет объект Аутентификации объектом пользователя из локальной бд
 */
@Component
public class FirebaseTokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FirebaseTokenAdapter tokenAdapter = (FirebaseTokenAdapter) authentication;

        FirebaseToken token;

        //todo exception handler!
        try {
            token = FirebaseAuth.getInstance().verifyIdToken(tokenAdapter.getBearer());
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getMessage());
        }

        String uuid = (String)token.getClaims().get("sub");

        User user;

        user = userRepository.findFirstByUuid(uuid);
        if (null == user) {
            user = new User();
            try {
                updateUserFields(user, uuid);
            } catch (FirebaseAuthException e) {
                throw new FirebaseAuthenticationException(e.getMessage());
            }
        }

        tokenAdapter.setPrincipal(user);
        tokenAdapter.setAuthenticated(true);

        return tokenAdapter;
    }

    private void updateUserFields(User user, String uuid) throws FirebaseAuthException {
        UserRecord userRecord;

        userRecord = FirebaseAuth.getInstance().getUser(uuid);

        user.setUuid(userRecord.getUid());
        user.setName(userRecord.getDisplayName());
        user.setEmail(userRecord.getEmail());
        user.setImageUrl(userRecord.getPhotoUrl());

        userRepository.save(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(FirebaseTokenAdapter.class);
    }
}
