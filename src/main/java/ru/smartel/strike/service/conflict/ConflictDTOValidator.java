package ru.smartel.strike.service.conflict;

import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface ConflictDTOValidator {
    void validateListQueryDTO(ConflictListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(ConflictRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(ConflictRequestDTO dto) throws DTOValidationException;
}
