package ru.smartel.strike.dto.request.locality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.service.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalityCreateRequestDTO {
    @JsonIgnore
    private Locale locale;
    private String name;
    private Long regionId;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}
