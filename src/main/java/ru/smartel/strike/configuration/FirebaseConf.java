package ru.smartel.strike.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import ru.smartel.strike.configuration.properties.FirebaseProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static java.util.Objects.isNull;

@Configuration
public class FirebaseConf {
    private final FirebaseProperties properties;

    public FirebaseConf(FirebaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FirebaseApp firebaseApp() {
        if (properties.getCredentials().isBlank()) {
            return null;
        }
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(properties.getCredentials().getBytes())))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    @Nullable
    public FirebaseMessaging messaging() {
        if (isNull(firebaseApp())) {
            return null;
        }
        return FirebaseMessaging.getInstance(firebaseApp());
    }

    @Bean
    @Nullable
    public FirebaseAuth auth() {
        if (isNull(firebaseApp())) {
            return null;
        }
        return FirebaseAuth.getInstance(firebaseApp());
    }
}
