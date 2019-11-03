package ru.smartel.strike.service.conflict;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.util.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class ConflictDTOValidatorImpl implements ConflictDTOValidator {

    @Override
    public void validateListQueryDTO(ConflictListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (dto.getPage() < 1) {
            addErrorMessage("page", new ValidationUtil.Min(1), errors);
        }

        if (dto.getPerPage() < 1) {
            addErrorMessage("per_page", new ValidationUtil.Min(1), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(ConflictCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new NotNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent( title -> {
                if (title.length() > 255) addErrorMessage("title", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_ru", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_en", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_es", new ValidationUtil.Max(255), errors);
            });
        }

        if (null == dto.getLatitude()) {
            addErrorMessage("latitude", new NotNull(), errors);
        }

        if (null == dto.getLongitude()) {
            addErrorMessage("longitude", new NotNull(), errors);
        }

        if (null != dto.getCompanyName()) {
            dto.getCompanyName().ifPresent(companyName -> {
                if (companyName.length() < 3) {
                    addErrorMessage("company_name", new Min(3), errors);
                } else if (companyName.length() > 500) {
                    addErrorMessage("company_name", new Max(500), errors);
                }
            });
        }

        if (null == dto.getConflictReasonId()) {
            addErrorMessage("conflict_reason_id", new Required(), errors);
        } else if (dto.getConflictReasonId().isEmpty()) {
            addErrorMessage("conflict_reason_id", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateUpdateDTO(ConflictUpdateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new NotNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflict_id", new NotNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent( title -> {
                if (title.length() > 255) addErrorMessage("title", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_ru", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_en", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("title_es", new ValidationUtil.Max(255), errors);
            });
        }

        dto.getCompanyName().ifPresent(companyName -> {
            if (companyName.length() < 3) {
                addErrorMessage("company_name", new Min(3), errors);
            } else if (companyName.length() > 500) {
                addErrorMessage("company_name", new Max(500), errors);
            }
        });

        throwIfErrorsExist(errors);
    }
}
