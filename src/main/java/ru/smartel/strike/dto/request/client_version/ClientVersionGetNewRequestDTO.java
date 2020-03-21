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
}
