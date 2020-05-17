package ru.smartel.strike.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.security.FirebaseAuthenticationException;
import ru.smartel.strike.service.user.UserService;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class FirebaseService {
    public static final String DEFAULT_USER_NAME = "unnamed user";
    private final FirebaseAuth firebaseAuth;
    private final UserService userService;

    public FirebaseService(@Nullable FirebaseAuth firebaseAuth, UserService userService) {
        this.firebaseAuth = firebaseAuth;
        this.userService = userService;
    }

    /**
     * Whether firebaseAuth bean is missing in context
     */
    public boolean firebaseAuthNotSet() {
        return isNull(firebaseAuth);
    }

    public FirebaseToken parseAndVerifyToken(String jwt) {
        if (isNull(firebaseAuth)) {
            throw new IllegalStateException("FirebaseAuth been not set");
        }
        try {
            return firebaseAuth.verifyIdToken(jwt);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getLocalizedMessage());
        }
    }

    public User getOrRegisterUser(String uid) {
        return userService.get(uid)
                .orElseGet(() -> {
                    var userRecord = queryUserInfo(uid);
                    return userService.register(userRecord.getUid(),
                            Optional.ofNullable(userRecord.getDisplayName()).orElse(DEFAULT_USER_NAME),
                            userRecord.getEmail(),
                            userRecord.getPhotoUrl());
                });
    }

    /**
     * Synchronize user attributes in DB with Firebase
     *
     * @param uid uid
     */
    public void syncUserFields(String uid) {
        var userRecord = queryUserInfo(uid);
        userService.update(userRecord.getUid(),
                Optional.ofNullable(userRecord.getDisplayName()).orElse(DEFAULT_USER_NAME),
                userRecord.getEmail(),
                userRecord.getPhotoUrl());
    }

    private UserRecord queryUserInfo(String uid) {
        if (isNull(firebaseAuth)) {
            throw new IllegalStateException("FirebaseAuth been not set");
        }
        try {
            return firebaseAuth.getUser(uid);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthenticationException(e.getLocalizedMessage());
        }
    }
}
