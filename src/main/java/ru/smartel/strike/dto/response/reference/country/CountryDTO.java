package ru.smartel.strike.dto.response.reference.country;

import lombok.Data;
import ru.smartel.strike.model.reference.Country;

@Data
public class CountryDTO {

    public CountryDTO(Country country) {
        id = country.getId();
    }

    private int id;
}
