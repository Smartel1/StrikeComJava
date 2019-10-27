package ru.smartel.strike.service.event;

import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface EventDTOValidator {
    void validateListQueryDTO(EventListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(EventRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(EventRequestDTO dto) throws DTOValidationException;
}
