package ru.smartel.strike.dto.response.reference.country;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.reference.Country;
import ru.smartel.strike.service.Locale;

public class CountryDTO extends NamesExtendableDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static CountryDTO of(Country country, Locale locale) {
        CountryDTO instance = new CountryDTO();
        instance.setNamesOf(country, locale);
        instance.setId(country.getId());
        return instance;
    }
}
