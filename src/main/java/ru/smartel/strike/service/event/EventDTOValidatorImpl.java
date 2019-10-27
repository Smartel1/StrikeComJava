package ru.smartel.strike.service.event;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BasePostDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventDTOValidatorImpl extends BasePostDTOValidator implements EventDTOValidator {

    @Override
    public void validateListQueryDTO(EventListRequestDTO dto) throws DTOValidationException {
        super.validateListQueryDTO(dto);
    }

    @Override
    public void validateStoreDTO(EventRequestDTO dto) throws DTOValidationException {
        super.validateStoreDTO(dto); //todo refactor to merge errors

        Map<String, String> errors = new HashMap<>();

        check(dto.getConflictId(), "conflict_id", errors).requiredOptional().notNull(true);
        check(dto.getDate(), "date", errors).requiredOptional().notNull();
        check(dto.getLatitude(), "latitude", errors).requiredOptional().notNull();
        check(dto.getLongitude(), "longitude", errors).requiredOptional().notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateUpdateDTO(EventRequestDTO dto) throws DTOValidationException {
        super.validateUpdateDTO(dto); //todo refactor to merge errors

        Map<String, String> errors = new HashMap<>();

        check(dto.getConflictId(), "conflict_id", errors).notNull(true);
        check(dto.getDate(), "date", errors).notNull();
        check(dto.getLatitude(), "latitude", errors).notNull();
        check(dto.getLongitude(), "longitude", errors).notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
