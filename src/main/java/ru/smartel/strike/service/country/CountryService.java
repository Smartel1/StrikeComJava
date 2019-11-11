package ru.smartel.strike.service.country;

import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.country.CountryDTO;
import ru.smartel.strike.service.Locale;

public interface CountryService {
    ListWrapperDTO<CountryDTO> list(String name, Locale locale);
    CountryDTO create(CountryCreateRequestDTO dto);
}
