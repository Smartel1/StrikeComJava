package ru.smartel.strike.service.conflict;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;

import java.util.*;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class ConflictDTOValidator {

    public void validateListQueryDTO(ConflictListRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (dto.getPage() < 1) {
            addErrorMessage("page", min(1), errors);
        }

        if (dto.getPerPage() < 1) {
            addErrorMessage("perPage", min(1), errors);
        }

        if (null != dto.getFilters() && null != dto.getFilters().getNear()) {
            if (null == dto.getFilters().getNear().getLat()) {
                addErrorMessage("filters.near.lat", notNull(), errors);
            }
            if (null == dto.getFilters().getNear().getLng()) {
                addErrorMessage("filters.near.lng", notNull(), errors);
            }
            if (null == dto.getFilters().getNear().getRadius()) {
                addErrorMessage("filters.near.radius", notNull(), errors);
            }
        }

        if (null != dto.getFilters() && null != dto.getFilters().getMainTypeIds()) {
            boolean wrongValuesExist = dto.getFilters().getMainTypeIds().stream()
                    .anyMatch(typeIdString -> !typeIdString.equals("null") && !typeIdString.matches("\\d+"));
            if (wrongValuesExist) {
                addErrorMessage("filters.mainTypeIds", numericCollection(), errors);
            }
        }

        if (dto.getSort() != null) {
            if (dto.getSort().getField() == null) {
                addErrorMessage("sort.field", notNull(), errors);
            } else {
                List<String> availableSortFields = Collections.singletonList("createdAt");
                if (!availableSortFields.contains(dto.getSort().getField())) {
                    addErrorMessage("sort.field", oneOf(availableSortFields), errors);
                }
            }
            if (dto.getSort().getOrder() == null) {
                addErrorMessage("sort.order", notNull(), errors);
            } else {
                List<String> availableSortOrders = Arrays.asList("asc", "desc");
                if (!availableSortOrders.contains(dto.getSort().getOrder())) {
                    addErrorMessage("sort.order", oneOf(availableSortOrders), errors);
                }
            }
        }

        throwIfErrorsExist(errors);
    }

    public void validateStoreDTO(ConflictCreateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", notNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent( title -> {
                if (title.length() > 255) addErrorMessage("title", max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleRu", max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEn", max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEs", max(255), errors);
            });
        }

        if (null != dto.getTitleDe()) {
            dto.getTitleDe().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleDe", max(255), errors);
            });
        }

        if (null == dto.getLatitude()) {
            addErrorMessage("latitude", notNull(), errors);
        }

        if (null == dto.getLongitude()) {
            addErrorMessage("longitude", notNull(), errors);
        }

        if (null != dto.getCompanyName()) {
            dto.getCompanyName().ifPresent(companyName -> {
                if (companyName.length() < 3) {
                    addErrorMessage("companyName", min(3), errors);
                } else if (companyName.length() > 500) {
                    addErrorMessage("companyName", max(500), errors);
                }
            });
        }

        if (null == dto.getConflictReasonId()) {
            addErrorMessage("conflictReasonId", required(), errors);
        } else if (dto.getConflictReasonId().isEmpty()) {
            addErrorMessage("conflictReasonId", notNull(), errors);
        }

        if (null == dto.getConflictResultId()) {
            addErrorMessage("conflictResultId", required(), errors);
        } else if (dto.getConflictResultId().isEmpty()) {
            addErrorMessage("conflictResultId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateUpdateDTO(ConflictUpdateRequestDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", notNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflictId", notNull(), errors);
        }

        if (null != dto.getTitle()) {
            dto.getTitle().ifPresent( title -> {
                if (title.length() > 255) addErrorMessage("title", max(255), errors);
            });
        }

        if (null != dto.getTitleRu()) {
            dto.getTitleRu().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleRu", max(255), errors);
            });
        }

        if (null != dto.getTitleEn()) {
            dto.getTitleEn().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEn", max(255), errors);
            });
        }

        if (null != dto.getTitleEs()) {
            dto.getTitleEs().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleEs", max(255), errors);
            });
        }

        if (null != dto.getTitleDe()) {
            dto.getTitleDe().ifPresent(title -> {
                if (title.length() > 255) addErrorMessage("titleDe", max(255), errors);
            });
        }

        if (null != dto.getCompanyName()) {
            dto.getCompanyName().ifPresent(companyName -> {
                if (companyName.length() < 3) {
                    addErrorMessage("companyName", min(3), errors);
                } else if (companyName.length() > 500) {
                    addErrorMessage("companyName", max(500), errors);
                }
            });
        }

        if (null != dto.getConflictResultId() && dto.getConflictResultId().isEmpty()) {
            addErrorMessage("conflictResultId", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
