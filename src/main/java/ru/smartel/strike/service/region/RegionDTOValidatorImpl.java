package ru.smartel.strike.service.region;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BaseDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegionDTOValidatorImpl extends BaseDTOValidator implements RegionDTOValidator {
    @Override
    public void validateCreateDTO(RegionCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getName(), "name", errors).notNull(true).minLength(1);
        check(dto.getCountryId(), "country_id", errors).notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
