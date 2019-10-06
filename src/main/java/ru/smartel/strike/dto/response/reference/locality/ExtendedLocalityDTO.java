package ru.smartel.strike.dto.response.reference.locality;

import ru.smartel.strike.dto.response.reference.region.RegionDTO;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.service.Locale;

/**
 * Extended потому что в первой версии (в php) не было вложенных сущностей
 */
public class ExtendedLocalityDTO {

    public ExtendedLocalityDTO(Locality locality, Locale locale) {
        id = locality.getId();
        name = locality.getName();
        region = new RegionDTO(locality.getRegion(), locale);
    }

    private int id;
    private String name;
    private RegionDTO region;

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

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }
}
