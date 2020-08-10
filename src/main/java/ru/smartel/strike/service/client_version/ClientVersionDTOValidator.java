package ru.smartel.strike.service.client_version;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class ClientVersionDTOValidator {

    public void validateListRequestDTO(ClientVersionGetNewRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getClientId()) {
            addErrorMessage("clientId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateStoreDTO(ClientVersionCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getClientId())
            addErrorMessage("clientId", notNull(), errors);

        if (null == dto.getVersion())
            addErrorMessage("version", notNull(), errors);

        if (null == dto.isRequired())
            addErrorMessage("required", notNull(), errors);

        if (null == dto.getDescriptionRu()) {
            addErrorMessage("descriptionRu", notNull(), errors);
        }

        if (null == dto.getDescriptionEn()) {
            addErrorMessage("descriptionEn", notNull(), errors);
        }

        if (null == dto.getDescriptionEs()) {
            addErrorMessage("descriptionEs", notNull(), errors);
        }

        if (null == dto.getDescriptionDe()) {
            addErrorMessage("descriptionDe", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
