package ru.smartel.strike.service.region;

import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.region.RegionDetailDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface RegionService {
    ListWrapperDTO<RegionDetailDTO> list(String name, Integer countryId, Locale locale);
    RegionDetailDTO create(RegionCreateRequestDTO dto, Locale locale) throws DTOValidationException;
}
