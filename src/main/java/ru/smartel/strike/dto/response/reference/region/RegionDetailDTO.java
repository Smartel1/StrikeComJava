package ru.smartel.strike.dto.response.reference.region;

import ru.smartel.strike.dto.response.ExtendableDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.service.Locale;

public class RegionDetailDTO extends ExtendableDTO {
    private int id;
    private String name;

    public RegionDetailDTO(Region region, Locale locale) {
        id = region.getId();
        name = region.getName();
        if (locale.equals(Locale.ALL)) {
            add("country_ru", region.getCountry().getNameRu());
            add("country_en", region.getCountry().getNameEn());
            add("country_es", region.getCountry().getNameEs());
        } else {
            add("country", region.getCountry().getNameByLocale(locale));
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
}
