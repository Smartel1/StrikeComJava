package ru.smartel.strike.dto.response.client_version;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.service.Locale;

public class ClientVersionDTO extends ExtendableDTO {
    private int id;
    private String version;
    private boolean isRequired;

    public static ClientVersionDTO of(ClientVersion clientVersion, Locale locale) {
        ClientVersionDTO instance = new ClientVersionDTO();
        instance.setId(clientVersion.getId());
        instance.setVersion(clientVersion.getVersion());
        instance.setRequired(clientVersion.isRequired());
        if (locale == Locale.ALL) {
            instance.add("description_ru", clientVersion.getDescriptionRu());
            instance.add("description_en", clientVersion.getDescriptionEn());
            instance.add("description_es", clientVersion.getDescriptionEs());
        } else {
            instance.add("description", clientVersion.getDescriptionByLocale(locale));
        }
        return instance;
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
