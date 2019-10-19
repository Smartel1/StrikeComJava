package ru.smartel.strike.service;

import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

public interface EventDTOValidator {
    void validateStore(EventRequestDTO dto) throws DTOValidationException;
    void validateUpdate(EventRequestDTO dto) throws DTOValidationException;
}
