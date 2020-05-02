package ru.smartel.strike.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {
    /**
     * Whether to use auth stub or not (autologin as moderator)
     */
    private boolean authStub;
    /**
     * Base64-decoded service-account json credentials
     * If not set -> messaging and authentication won`t work
     */
    private String credentials;

    public boolean isAuthStub() {
        return authStub;
    }

    public void setAuthStub(boolean authStub) {
        this.authStub = authStub;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
