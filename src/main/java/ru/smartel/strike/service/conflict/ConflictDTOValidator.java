package ru.smartel.strike.service.conflict;

import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface ConflictDTOValidator {
    void validateListQueryDTO(ConflictListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(ConflictCreateRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(ConflictCreateRequestDTO dto) throws DTOValidationException;
}
