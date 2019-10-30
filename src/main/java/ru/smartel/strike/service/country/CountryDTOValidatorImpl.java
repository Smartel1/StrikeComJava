package ru.smartel.strike.service.country;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BaseDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountryDTOValidatorImpl extends BaseDTOValidator implements CountryDTOValidator {
    @Override
    public void validateCreateDTO(CountryCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getNameRu(), "name_ru", errors).notNull(true).minLength(1);
        check(dto.getNameEn(), "name_en", errors).notNull(true).minLength(1);
        check(dto.getNameEs(), "name_es", errors).notNull(true).minLength(1);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
