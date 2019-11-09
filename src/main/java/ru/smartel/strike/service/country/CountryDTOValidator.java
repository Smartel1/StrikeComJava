package ru.smartel.strike.service.country;

import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;

public interface CountryDTOValidator {
    void validateCreateDTO(CountryCreateRequestDTO dto);
}
