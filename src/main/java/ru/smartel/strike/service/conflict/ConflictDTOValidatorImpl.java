package ru.smartel.strike.service.conflict;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.util.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class ConflictDTOValidatorImpl implements ConflictDTOValidator {

    @Override
    public void validateListQueryDTO(ConflictListRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (dto.getPage() < 1) {
            addErrorMessage("page", new ValidationUtil.Min(1), errors);
        }

        if (dto.getPerPage() < 1) {
            addErrorMessage("perPage", new ValidationUtil.Min(1), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(ConflictCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

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
                if (title.length() > 255) addErrorMessage("titleRu", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEn", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEs", new ValidationUtil.Max(255), errors);
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
                    addErrorMessage("companyName", new Min(3), errors);
                } else if (companyName.length() > 500) {
                    addErrorMessage("companyName", new Max(500), errors);
                }
            });
        }

        if (null == dto.getConflictReasonId()) {
            addErrorMessage("conflictReasonId", new Required(), errors);
        } else if (dto.getConflictReasonId().isEmpty()) {
            addErrorMessage("conflictReasonId", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateUpdateDTO(ConflictUpdateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new NotNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflictId", new NotNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent( title -> {
                if (title.length() > 255) addErrorMessage("title", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleRu", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEn", new ValidationUtil.Max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEs", new ValidationUtil.Max(255), errors);
            });
        }

        dto.getCompanyName().ifPresent(companyName -> {
            if (companyName.length() < 3) {
                addErrorMessage("companyName", new Min(3), errors);
            } else if (companyName.length() > 500) {
                addErrorMessage("companyName", new Max(500), errors);
            }
        });

        throwIfErrorsExist(errors);
    }
}
