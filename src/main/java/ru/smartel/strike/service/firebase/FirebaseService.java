package ru.smartel.strike.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.smartel.strike.service.user.UserService;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class FirebaseService {
    private final FirebaseAuth firebaseAuth;
    private final UserService userService;

    public FirebaseService(@Nullable FirebaseAuth firebaseAuth, UserService userService) {
        this.firebaseAuth = firebaseAuth;
        this.userService = userService;
    }

    /**
     * Whether firebaseAuth been is missing in context
     */
    public boolean firebaseAuthNotSet() {
        return isNull(firebaseAuth);
    }

    public FirebaseToken parseAndVerifyToken(String jwt) throws FirebaseAuthException {
        if (isNull(firebaseAuth)) {
            throw new IllegalStateException("FirebaseAuth been not set");
        }
        return firebaseAuth.verifyIdToken(jwt);
    }

    public UserRecord getUser(String uid) throws FirebaseAuthException {
        if (isNull(firebaseAuth)) {
            throw new IllegalStateException("FirebaseAuth been not set");
        }
        return firebaseAuth.getUser(uid);
    }

    public void updateUserFields(String uid) throws FirebaseAuthException {
        var userRecord = getUser(uid);
        userService.update(userRecord.getUid(),
                Optional.ofNullable(userRecord.getDisplayName()).orElse("unnamed user"),
                userRecord.getEmail(),
                userRecord.getPhotoUrl());
    }
}
