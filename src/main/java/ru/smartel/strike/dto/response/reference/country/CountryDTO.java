package ru.smartel.strike.dto.response.reference.country;

import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.reference.Country;
import ru.smartel.strike.service.Locale;

public class CountryDTO extends NamesExtendableDTO {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryDTO(Country country, Locale locale) {
        super(country, locale);
        id = country.getId();
    }
}
