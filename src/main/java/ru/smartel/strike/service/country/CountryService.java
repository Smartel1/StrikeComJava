package ru.smartel.strike.service.country;

import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.dto.response.reference.locality.LocalityDetailDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface CountryService {
    ListWrapperDTO<CountryDTO> list(String name, Locale locale);
    CountryDTO create(CountryCreateRequestDTO dto, Locale locale) throws DTOValidationException;
}
