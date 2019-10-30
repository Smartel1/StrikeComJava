package ru.smartel.strike.service.locality;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BaseDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class LocalityDTOValidatorImpl extends BaseDTOValidator implements LocalityDTOValidator {
    @Override
    public void validateCreateDTO(LocalityCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getName(), "name", errors).notNull(true).minLength(1);
        check(dto.getRegionId(), "region_id", errors).notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
