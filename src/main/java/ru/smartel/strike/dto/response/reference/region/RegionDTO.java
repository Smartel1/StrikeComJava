package ru.smartel.strike.dto.response.reference.region;

import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.service.Locale;

public class RegionDTO {

    public RegionDTO(Region region, Locale locale) {
        id = region.getId();
        name = region.getName();
        country = new CountryDTO(region.getCountry(), locale);
    }

    private int id;
    private String name;
    private CountryDTO country;

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
