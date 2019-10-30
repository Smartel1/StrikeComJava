package ru.smartel.strike.dto.response.reference.locality;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.service.Locale;

public class LocalityDetailDTO extends ExtendableDTO {
    private int id;
    private String name;
    private String region;

    public LocalityDetailDTO(Locality locality, Locale locale) {
        id = locality.getId();
        name = locality.getName();
        region = locality.getRegion().getName();
        if (locale.equals(Locale.ALL)) {
            add("country_ru", locality.getRegion().getCountry().getNameRu());
            add("country_en", locality.getRegion().getCountry().getNameEn());
            add("country_es", locality.getRegion().getCountry().getNameEs());
        } else {
            add("country", locality.getRegion().getCountry().getNameByLocale(locale));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
