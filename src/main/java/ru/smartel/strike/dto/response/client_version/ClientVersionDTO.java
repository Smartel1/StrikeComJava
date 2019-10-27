package ru.smartel.strike.dto.response.client_version;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.service.Locale;

public class ClientVersionDTO extends ExtendableDTO {
    private int id;
    private String version;
    private boolean isRequired;

    public ClientVersionDTO(ClientVersion clientVersion, Locale locale) {
        this.id = clientVersion.getId();
        this.version = clientVersion.getVersion();
        this.isRequired = clientVersion.isRequired();
        if (locale == Locale.ALL) {
            add("description_ru", clientVersion.getDescriptionRu());
            add("description_en", clientVersion.getDescriptionEn());
            add("description_es", clientVersion.getDescriptionEs());
        } else {
            add("description", clientVersion.getDescriptionByLocale(locale));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
