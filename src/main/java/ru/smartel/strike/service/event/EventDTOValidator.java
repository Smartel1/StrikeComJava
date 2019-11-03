package ru.smartel.strike.service.event;

import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface EventDTOValidator {
    void validateListQueryDTO(EventListRequestDTO dto) throws DTOValidationException;
    void validateStoreDTO(EventCreateRequestDTO dto) throws DTOValidationException;
    void validateUpdateDTO(EventCreateRequestDTO dto) throws DTOValidationException;
}
