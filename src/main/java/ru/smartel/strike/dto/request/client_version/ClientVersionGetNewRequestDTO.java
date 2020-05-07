package ru.smartel.strike.dto.request.client_version;

import io.swagger.annotations.ApiParam;
import ru.smartel.strike.service.Locale;

public class ClientVersionGetNewRequestDTO {
    private Locale locale;
    @ApiParam(value = "Текущая версия приложения на утройстве")
    private String currentVersion;
    @ApiParam(value = "Идентификатор клиента", example = "org.nrstudio.strikecom")
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

    public ClientVersionGetNewRequestDTO() {};

    public static class Builder {
        private Locale locale;
        private String currentVersion;
        private String clientId;

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder currentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public ClientVersionGetNewRequestDTO build() {
            return new ClientVersionGetNewRequestDTO(this);
        }
    }

    private ClientVersionGetNewRequestDTO(Builder b) {
        locale = b.locale;
        currentVersion = b.currentVersion;
        clientId = b.clientId;
    }
}
