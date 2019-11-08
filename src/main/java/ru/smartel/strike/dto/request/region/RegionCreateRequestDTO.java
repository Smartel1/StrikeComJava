package ru.smartel.strike.dto.request.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.service.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionCreateRequestDTO {
    @JsonIgnore
    private Locale locale;
    private String name;
    private Integer countryId;

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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}