package ru.smartel.strike.service.impl;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.BaseDTOValidator;
import ru.smartel.strike.service.ConflictDTOValidator;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConflictDTOValidatorImpl extends BaseDTOValidator implements ConflictDTOValidator {

    @Override
    public void validateListQueryDTO(ConflictListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getPage(), "page", errors).min(1);
        check(dto.getPerPage(), "per_page", errors).min(1);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateStoreDTO(ConflictRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getTitle(), "title", errors).maxLength(255);
        check(dto.getTitleRu(), "title_ru", errors).maxLength(255);
        check(dto.getTitleEn(), "title_en", errors).maxLength(255);
        check(dto.getTitleEs(), "title_es", errors).maxLength(255);
        check(dto.getLatitude(), "latitude", errors).notNull();
        check(dto.getLongitude(), "longitude", errors).notNull();
        check(dto.getCompanyName(), "company_name", errors).minLength(3).maxLength(500);
        check(dto.getConflictReasonId(), "conflict_reason_id", errors).requiredOptional().notNull();

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }

    @Override
    public void validateUpdateDTO(ConflictRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        check(dto.getTitle(), "title", errors).maxLength(255);
        check(dto.getTitleRu(), "title_ru", errors).maxLength(255);
        check(dto.getTitleEn(), "title_en", errors).maxLength(255);
        check(dto.getTitleEs(), "title_es", errors).maxLength(255);
        check(dto.getCompanyName(), "company_name", errors).minLength(3).maxLength(500);

        if (!errors.isEmpty()) {
            throw new DTOValidationException("validation errors", errors);
        }
    }
}
