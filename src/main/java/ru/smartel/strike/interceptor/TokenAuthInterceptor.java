package ru.smartel.strike.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.smartel.strike.exception.UnauthtenticatedException;
import ru.smartel.strike.model.User;
import ru.smartel.strike.repository.UserRepository;
import ru.smartel.strike.service.AuthService;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnauthtenticatedException {

        String bearer = request.getHeader("Authorization");

        if (bearer == null) return true;

        if (!bearer.startsWith("Bearer ")) {
            throw new UnauthtenticatedException("Некорректный заголовок");
        }

        bearer = bearer.substring(7);

        FirebaseToken token;

        try {
            token = FirebaseAuth.getInstance().verifyIdToken(bearer);
        } catch (FirebaseAuthException e) {
            throw new UnauthtenticatedException(e.getMessage());
        }

        String uuid = (String)token.getClaims().get("sub");

        User user;

        try {
            user = userRepository.findOneByUuid(uuid);
        } catch (PersistenceException e) {
            user = new User();
            updateUserFields(user, uuid);
        }

        authService.login(user);

        return true;
    }

    private void updateUserFields(User user, String uuid) throws UnauthtenticatedException {
        UserRecord userRecord;

        try {
            userRecord = FirebaseAuth.getInstance().getUser(uuid);
        } catch (FirebaseAuthException e) {
            throw new UnauthtenticatedException(e.getMessage());
        }

        user.setUuid(userRecord.getUid());
        user.setName(userRecord.getDisplayName());
        user.setEmail(userRecord.getEmail());
        user.setImageUrl(userRecord.getPhotoUrl());

        userRepository.save(user);
    }
}
