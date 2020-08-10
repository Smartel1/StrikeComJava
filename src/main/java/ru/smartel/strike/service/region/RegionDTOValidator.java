package ru.smartel.strike.service.region;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class RegionDTOValidator {
    public void validateCreateDTO(RegionCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getName()) {
            addErrorMessage("name", notNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", min(1), errors);
        }

        if (null == dto.getCountryId()) {
            addErrorMessage("countryId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
