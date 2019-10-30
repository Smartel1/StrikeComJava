package ru.smartel.strike.service.country;

import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface CountryDTOValidator {
    void validateCreateDTO(CountryCreateRequestDTO dto) throws DTOValidationException;
}
