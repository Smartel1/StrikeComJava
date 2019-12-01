package ru.smartel.strike.dto.response.reference.locality;

import ru.smartel.strike.dto.response.reference.region.RegionDTO;
import ru.smartel.strike.entity.reference.Locality;
import ru.smartel.strike.service.Locale;

/**
 * Extended потому что в первой версии (в php) не было вложенных сущностей
 */
public class ExtendedLocalityDTO {

    private long id;
    private String name;
    private RegionDTO region;

    public static ExtendedLocalityDTO of(Locality locality, Locale locale) {
        ExtendedLocalityDTO instance = new ExtendedLocalityDTO();
        instance.setId(locality.getId());
        instance.setName(locality.getName());
        instance.setRegion(RegionDTO.of(locality.getRegion(), locale));
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

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }
}
