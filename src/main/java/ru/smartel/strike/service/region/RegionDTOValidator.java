package ru.smartel.strike.service.region;

import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;

public interface RegionDTOValidator {
    void validateCreateDTO(RegionCreateRequestDTO dto);
}
