package ru.smartel.strike.service.region;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.region.RegionCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.Min;
import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Service
public class RegionDTOValidator {
    public void validateCreateDTO(RegionCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getName()) {
            addErrorMessage("name", new NotNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", new Min(1), errors);
        }

        if (null == dto.getCountryId()) {
            addErrorMessage("countryId", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
