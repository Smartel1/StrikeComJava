package ru.smartel.strike.service.country;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class CountryDTOValidator {
    public void validateCreateDTO(CountryCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getNameRu()) {
            addErrorMessage("nameRu", notNull(), errors);
        } else if (dto.getNameRu().length() < 1) {
            addErrorMessage("nameRu", min(1), errors);
        }

        if (null == dto.getNameEn()) {
            addErrorMessage("nameEn", notNull(), errors);
        } else if (dto.getNameEn().length() < 1) {
            addErrorMessage("nameEn", min(1), errors);
        }

        if (null == dto.getNameEs()) {
            addErrorMessage("nameEs", notNull(), errors);
        } else if (dto.getNameEs().length() < 1) {
            addErrorMessage("nameEs", min(1), errors);
        }

        if (null == dto.getNameDe()) {
            addErrorMessage("nameDe", notNull(), errors);
        } else if (dto.getNameDe().length() < 1) {
            addErrorMessage("nameDe", min(1), errors);
        }

        throwIfErrorsExist(errors);
    }
}
