package ru.smartel.strike.service.locality;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class LocalityDTOValidator {
    public void validateCreateDTO(LocalityCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getName()) {
            addErrorMessage("name", notNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", min(1), errors);
        }

        if (null == dto.getRegionId()) {
            addErrorMessage("regionId", notNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
