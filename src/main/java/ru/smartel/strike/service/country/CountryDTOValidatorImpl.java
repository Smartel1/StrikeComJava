package ru.smartel.strike.service.country;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class CountryDTOValidatorImpl implements CountryDTOValidator {
    @Override
    public void validateCreateDTO(CountryCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getNameRu()) {
            addErrorMessage("name_ru", new NotNull(), errors);
        } else if (dto.getNameRu().length() < 1) {
            addErrorMessage("name_ru", new Min(1), errors);
        }

        if (null == dto.getNameEn()) {
            addErrorMessage("name_en", new NotNull(), errors);
        } else if (dto.getNameEn().length() < 1) {
            addErrorMessage("name_en", new Min(1), errors);
        }

        if (null == dto.getNameEs()) {
            addErrorMessage("name_es", new NotNull(), errors);
        } else if (dto.getNameEn().length() < 1) {
            addErrorMessage("name_es", new Min(1), errors);
        }

        throwIfErrorsExist(errors);
    }
}
