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
            instance.add("countryRu", region.getCountry().getNameRu());
            instance.add("countryEn", region.getCountry().getNameEn());
            instance.add("countryEs", region.getCountry().getNameEs());
            instance.add("countryDe", region.getCountry().getNameDe());
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
