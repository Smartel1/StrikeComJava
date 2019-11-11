package ru.smartel.strike.service.conflict;

import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;

public interface ConflictDTOValidator {
    void validateListQueryDTO(ConflictListRequestDTO dto);
    void validateStoreDTO(ConflictCreateRequestDTO dto);
    void validateUpdateDTO(ConflictUpdateRequestDTO dto);
}
