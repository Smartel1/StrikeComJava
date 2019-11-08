package ru.smartel.strike.dto.response.reference.region;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.service.Locale;

public class RegionDetailDTO extends ExtendableDTO {
    private int id;
    private String name;

    public static RegionDetailDTO of(Region region, Locale locale) {
        RegionDetailDTO instance = new RegionDetailDTO();
        instance.setId(region.getId());
        instance.setName(region.getName());
        if (locale.equals(Locale.ALL)) {
            instance.add("country_ru", region.getCountry().getNameRu());
            instance.add("country_en", region.getCountry().getNameEn());
            instance.add("country_es", region.getCountry().getNameEs());
        } else {
            instance.add("country", region.getCountry().getNameByLocale(locale));
        }
        return instance;
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
}
