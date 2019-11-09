package ru.smartel.strike.service.locality;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.locality.LocalityCreateRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.Min;
import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Component
public class LocalityDTOValidatorImpl implements LocalityDTOValidator {
    @Override
    public void validateCreateDTO(LocalityCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getName()) {
            addErrorMessage("name", new NotNull(), errors);
        } else if (dto.getName().length() < 1) {
            addErrorMessage("name", new Min(1), errors);
        }

        if (null == dto.getRegionId()) {
            addErrorMessage("regionId", new NotNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
