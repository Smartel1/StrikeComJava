package ru.smartel.strike.service.client_version;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class ClientVersionDTOValidatorImpl implements ClientVersionDTOValidator {

    @Override
    public void validateListRequestDTO(ClientVersionGetNewRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getClientId()) {
            addErrorMessage("client_id", new NotNull(), errors);
        }

        if (null == dto.getCurrentVersion()) {
            addErrorMessage("current_version", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(ClientVersionCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getClientId())
            addErrorMessage("client_id", new NotNull(), errors);

        if (null == dto.getVersion())
            addErrorMessage("version", new NotNull(), errors);

        if (null == dto.isRequired())
            addErrorMessage("required", new NotNull(), errors);

        if (null == dto.getDescriptionRu()) {
            addErrorMessage("description_ru", new NotNull(), errors);
        } else if (dto.getDescriptionRu().length() > 500){
            addErrorMessage("description_ru", new Max(500), errors);
        }

        if (null == dto.getDescriptionEn()) {
            addErrorMessage("description_en", new NotNull(), errors);
        } else if (dto.getDescriptionEn().length() > 500){
            addErrorMessage("description_en", new Max(500), errors);
        }

        if (null == dto.getDescriptionEs()) {
            addErrorMessage("description_es", new NotNull(), errors);
        } else if (dto.getDescriptionEs().length() > 500){
            addErrorMessage("description_es", new Max(500), errors);
        }

        throwIfErrorsExist(errors);
    }
}
