package ru.smartel.strike.dto.response.reference.country;

import lombok.Data;
import ru.smartel.strike.model.reference.Country;
import ru.smartel.strike.service.Locale;

@Data
public class LocalizedCountryDTO extends CountryDTO {

    public LocalizedCountryDTO(Country country, Locale locale) {
        super(country);
        switch (locale) {
            case RU: {
                name = country.getNameRu();
                break;
            }
            case EN: {
                name = country.getNameEn();
                break;
            }
            case ES: {
                name = country.getNameEs();
                break;
            }
        }
    }

    private String name;
}
