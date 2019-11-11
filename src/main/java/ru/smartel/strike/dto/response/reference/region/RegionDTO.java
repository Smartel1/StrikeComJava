package ru.smartel.strike.dto.response.reference.region;

import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.service.Locale;

public class RegionDTO {

    private int id;
    private String name;
    private CountryDTO country;

    public static RegionDTO of(Region region, Locale locale) {
        RegionDTO instance = new RegionDTO();
        instance.setId(region.getId());
        instance.setName(region.getName());
        instance.setCountry(CountryDTO.of(region.getCountry(), locale));
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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }
}
