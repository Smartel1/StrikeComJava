package ru.smartel.strike.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.smartel.strike.service.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_versions")
public class ClientVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private String version;

    @Column(name = "client_id")
    private String clientId;

    @Column
    private boolean required;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_es")
    private String descriptionEs;

    @Column(name = "description_de")
    private String descriptionDe;

    public long getId() {
        return id;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getDescriptionByLocale(Locale locale) {
        switch (locale) {
            case RU:
                return getDescriptionRu();
            case EN:
                return getDescriptionEn();
            case ES:
                return getDescriptionEs();
            case DE:
                return getDescriptionDe();
            default:
                return "";
        }
    }
}
