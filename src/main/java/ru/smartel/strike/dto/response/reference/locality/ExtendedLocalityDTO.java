package ru.smartel.strike.dto.response.reference.locality;

import lombok.Data;
import ru.smartel.strike.dto.response.reference.region.RegionDTO;
import ru.smartel.strike.model.reference.Locality;
import ru.smartel.strike.service.Locale;

@Data
public class ExtendedLocalityDTO {

    public ExtendedLocalityDTO(Locality locality, Locale locale) {
        id = locality.getId();
        name = locality.getName();
        region = new RegionDTO(locality.getRegion(), locale);
    }

    private int id;
    private String name;
    private RegionDTO region;
}
