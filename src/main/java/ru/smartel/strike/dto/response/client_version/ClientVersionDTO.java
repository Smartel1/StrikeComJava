package ru.smartel.strike.dto.response.client_version;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.ClientVersion;
import ru.smartel.strike.service.Locale;

import java.util.Objects;

public class ClientVersionDTO extends ExtendableDTO {
    private long id;
    private String version;
    private boolean isRequired;

    public static ClientVersionDTO of(ClientVersion clientVersion, Locale locale) {
        ClientVersionDTO instance = new ClientVersionDTO();
        instance.setId(clientVersion.getId());
        instance.setVersion(clientVersion.getVersion());
        instance.setRequired(clientVersion.isRequired());
        if (locale == Locale.ALL) {
            instance.add("descriptionRu", clientVersion.getDescriptionRu());
            instance.add("descriptionEn", clientVersion.getDescriptionEn());
            instance.add("descriptionEs", clientVersion.getDescriptionEs());
            instance.add("descriptionDe", clientVersion.getDescriptionDe());
        } else {
            instance.add("description", clientVersion.getDescriptionByLocale(locale));
        }
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientVersionDTO that = (ClientVersionDTO) o;
        return id == that.id &&
                isRequired == that.isRequired &&
                Objects.equals(version, that.version) &&
                Objects.equals(getOptionalFields(), that.getOptionalFields());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, isRequired, getOptionalFields());
    }
}
