package ru.smartel.strike.service.country;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.country.CountryCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.Min;
import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Component
public class CountryDTOValidator {
    public void validateCreateDTO(CountryCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getNameRu()) {
            addErrorMessage("nameRu", new NotNull(), errors);
        } else if (dto.getNameRu().length() < 1) {
            addErrorMessage("nameRu", new Min(1), errors);
        }

        if (null == dto.getNameEn()) {
            addErrorMessage("nameEn", new NotNull(), errors);
        } else if (dto.getNameEn().length() < 1) {
            addErrorMessage("nameEn", new Min(1), errors);
        }

        if (null == dto.getNameEs()) {
            addErrorMessage("nameEs", new NotNull(), errors);
        } else if (dto.getNameEs().length() < 1) {
            addErrorMessage("nameEs", new Min(1), errors);
        }

        if (null == dto.getNameDe()) {
            addErrorMessage("nameDe", new NotNull(), errors);
        } else if (dto.getNameDe().length() < 1) {
            addErrorMessage("nameDe", new Min(1), errors);
        }

        throwIfErrorsExist(errors);
    }
}
