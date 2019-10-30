package ru.smartel.strike.service.locality;

import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface LocalityDTOValidator {
    void validateCreateDTO(LocalityCreateRequestDTO dto) throws DTOValidationException;
}
