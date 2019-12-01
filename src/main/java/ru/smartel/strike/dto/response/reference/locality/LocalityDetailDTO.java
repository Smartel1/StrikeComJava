package ru.smartel.strike.dto.response.reference.locality;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.service.Locale;

public class LocalityDetailDTO extends ExtendableDTO {
    private long id;
    private String name;
    private String region;

    public static LocalityDetailDTO of(Locality locality, Locale locale) {
        LocalityDetailDTO instance = new LocalityDetailDTO();
        instance.setId(locality.getId());
        instance.setName(locality.getName());
        instance.setRegion(locality.getRegion().getName());
        if (locale.equals(Locale.ALL)) {
            instance.add("countryRu", locality.getRegion().getCountry().getNameRu());
            instance.add("countryEn", locality.getRegion().getCountry().getNameEn());
            instance.add("countryEs", locality.getRegion().getCountry().getNameEs());
        } else {
            instance.add("country", locality.getRegion().getCountry().getNameByLocale(locale));
        }
        return instance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
