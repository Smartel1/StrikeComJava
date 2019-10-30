package ru.smartel.strike.service.region;

import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface RegionDTOValidator {
    void validateCreateDTO(RegionCreateRequestDTO dto) throws DTOValidationException;
}
