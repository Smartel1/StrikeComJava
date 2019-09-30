package ru.smartel.strike.dto.response.reference.region;

import lombok.Data;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.entity.reference.Region;
import ru.smartel.strike.service.Locale;

@Data
public class RegionDTO {

    public RegionDTO(Region region, Locale locale) {
        id = region.getId();
        name = region.getName();
        country = new CountryDTO(region.getCountry(), locale);
    }

    private int id;
    private String name;
    private CountryDTO country;
}
