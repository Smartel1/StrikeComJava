package ru.smartel.strike.service.locality;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class LocalityDTOValidatorImpl implements LocalityDTOValidator {
    @Override
    public void validateCreateDTO(LocalityCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getName()) {
            addErrorMessage("name", new NotNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", new Min(1), errors);
        }

        if (null == dto.getRegionId()) {
            addErrorMessage("region_id", new NotNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
