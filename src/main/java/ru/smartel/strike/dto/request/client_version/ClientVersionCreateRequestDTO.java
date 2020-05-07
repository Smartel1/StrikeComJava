package ru.smartel.strike.dto.request.client_version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.service.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientVersionCreateRequestDTO {
    @JsonIgnore
    private Locale locale;
    private String version;
    private String clientId;
    private Boolean required;
    private String descriptionRu;
    private String descriptionEn;
    private String descriptionEs;
    private String descriptionDe;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionEs() {
        return descriptionEs;
    }

    public void setDescriptionEs(String descriptionEs) {
        this.descriptionEs = descriptionEs;
    }

    public String getDescriptionDe() {
        return descriptionDe;
    }

    public void setDescriptionDe(String descriptionDe) {
        this.descriptionDe = descriptionDe;
    }

    public static class Builder {
        private Locale locale;
        private String version;
        private String clientId;
        private Boolean required;
        private String descriptionRu;
        private String descriptionEn;
        private String descriptionEs;
        private String descriptionDe;

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder required(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder descriptionRu(String descriptionRu) {
            this.descriptionRu = descriptionRu;
            return this;
        }

        public Builder descriptionDe(String descriptionDe) {
            this.descriptionDe = descriptionDe;
            return this;
        }

        public Builder descriptionEn(String descriptionEn) {
            this.descriptionEn = descriptionEn;
            return this;
        }

        public Builder descriptionEs(String descriptionEs) {
            this.descriptionEs = descriptionEs;
            return this;
        }

        public ClientVersionCreateRequestDTO build() {
            return new ClientVersionCreateRequestDTO(this);
        }
    }

    private ClientVersionCreateRequestDTO(Builder b) {
        locale = b.locale;
        version = b.version;
        clientId = b.clientId;
        required = b.required;
        descriptionRu = b.descriptionRu;
        descriptionEn = b.descriptionEn;
        descriptionEs = b.descriptionEs;
        descriptionDe = b.descriptionDe;
    }
}
