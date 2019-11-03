package ru.smartel.strike.dto.request.client_version;

import ru.smartel.strike.service.Locale;

public class ClientVersionGetNewRequestDTO {
    private Locale locale;
    private String currentVersion;
    private String clientId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
