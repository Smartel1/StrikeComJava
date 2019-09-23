package ru.smartel.strike.dto.response.reference.country;

import lombok.Data;
import ru.smartel.strike.model.reference.Country;

@Data
public class InternationalizedCountryDTO extends CountryDTO {

    public InternationalizedCountryDTO(Country country) {
        super(country);
        nameRu = country.getNameRu();
        nameEn = country.getNameEn();
        nameEs = country.getNameEs();
    }

    private String nameRu;
    private String nameEn;
    private String nameEs;
}
