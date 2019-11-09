package ru.smartel.strike.service.client_version;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.Max;
import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Component
public class ClientVersionDTOValidatorImpl implements ClientVersionDTOValidator {

    @Override
    public void validateListRequestDTO(ClientVersionGetNewRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getClientId()) {
            addErrorMessage("clientId", new NotNull(), errors);
        }

        if (null == dto.getCurrentVersion()) {
            addErrorMessage("currentVersion", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(ClientVersionCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getClientId())
            addErrorMessage("clientId", new NotNull(), errors);

        if (null == dto.getVersion())
            addErrorMessage("version", new NotNull(), errors);

        if (null == dto.isRequired())
            addErrorMessage("required", new NotNull(), errors);

        if (null == dto.getDescriptionRu()) {
            addErrorMessage("descriptionRu", new NotNull(), errors);
        } else if (dto.getDescriptionRu().length() > 500){
            addErrorMessage("descriptionRu", new Max(500), errors);
        }

        if (null == dto.getDescriptionEn()) {
            addErrorMessage("descriptionEn", new NotNull(), errors);
        } else if (dto.getDescriptionEn().length() > 500){
            addErrorMessage("descriptionEn", new Max(500), errors);
        }

        if (null == dto.getDescriptionEs()) {
            addErrorMessage("descriptionEs", new NotNull(), errors);
        } else if (dto.getDescriptionEs().length() > 500){
            addErrorMessage("descriptionEs", new Max(500), errors);
        }

        throwIfErrorsExist(errors);
    }
}
