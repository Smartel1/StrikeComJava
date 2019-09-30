package ru.smartel.strike.dto.response.reference.country;

import lombok.Data;
import ru.smartel.strike.dto.response.NamesExtendableDTO;
import ru.smartel.strike.entity.reference.Country;
import ru.smartel.strike.service.Locale;

@Data
public class CountryDTO extends NamesExtendableDTO {

    public CountryDTO(Country country, Locale locale) {
        super(country, locale);
        id = country.getId();
    }

    private int id;
}
