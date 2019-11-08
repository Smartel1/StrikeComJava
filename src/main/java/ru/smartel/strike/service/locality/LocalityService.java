package ru.smartel.strike.service.locality;

import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.reference.locality.LocalityDetailDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface LocalityService {
    ListWrapperDTO<LocalityDetailDTO> list(String name, Integer regionId, Locale locale);
    LocalityDetailDTO create(LocalityCreateRequestDTO dto) throws DTOValidationException;
}