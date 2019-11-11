package ru.smartel.strike.service.locality;

import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;

public interface LocalityDTOValidator {
    void validateCreateDTO(LocalityCreateRequestDTO dto);
}
