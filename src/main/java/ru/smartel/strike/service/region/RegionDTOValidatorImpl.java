package ru.smartel.strike.service.region;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class RegionDTOValidatorImpl implements RegionDTOValidator {
    @Override
    public void validateCreateDTO(RegionCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getName()) {
            addErrorMessage("name", new NotNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", new Min(1), errors);
        }

        if (null == dto.getCountryId()) {
            addErrorMessage("country_id", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
