package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.dto.request.client_version.ClientVersionGetNewRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.ClientVersionDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientVersionDTOValidatorImpl extends BasePostDTOValidator implements ClientVersionDTOValidator {

    @Override
    public void validateListRequestDTO(ClientVersionGetNewRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getClientId(), "client_id", errors).notNull();
        check(dto.getCurrentVersion(), "current_version", errors).notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateStoreDTO(ClientVersionCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getClientId(), "client_id", errors).notNull();
        check(dto.getVersion(), "version", errors).notNull();
        check(dto.isRequired(), "required", errors).notNull();
        check(dto.getDescriptionRu(), "description_ru", errors).notNull().maxLength(500);
        check(dto.getDescriptionEn(), "description_en", errors).notNull().maxLength(500);
        check(dto.getDescriptionEs(), "description_es", errors).notNull().maxLength(500);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
