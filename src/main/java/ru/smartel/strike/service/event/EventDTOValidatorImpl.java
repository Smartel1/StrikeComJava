package ru.smartel.strike.service.event;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.service.validation.BasePostDTOValidator;
import ru.smartel.strike.util.ValidationUtil;

import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.NotNull;
import static ru.smartel.strike.util.ValidationUtil.addErrorMessage;
import static ru.smartel.strike.util.ValidationUtil.throwIfErrorsExist;

@Component
public class EventDTOValidatorImpl extends BasePostDTOValidator implements EventDTOValidator {

    @Override
    public void validateListQueryDTO(EventListRequestDTO dto) {
        Map<String, List<String>> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        if (null != dto.getFilters() && null != dto.getFilters().getNear()) {
            if (null == dto.getFilters().getNear().getLat()) {
                addErrorMessage("filters.near.lat", new NotNull(), errors);
            }
            if (null == dto.getFilters().getNear().getLng()) {
                addErrorMessage("filters.near.lng", new NotNull(), errors);
            }
            if (null == dto.getFilters().getNear().getRadius()) {
                addErrorMessage("filters.near.radius", new NotNull(), errors);
            }
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(EventCreateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflictId", new ValidationUtil.Required(), errors);
        } else if (dto.getConflictId().isEmpty()) {
            addErrorMessage("conflictId", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getLatitude()) {
            addErrorMessage("latitude", new ValidationUtil.Required(), errors);
        } else if (dto.getLatitude().isEmpty()) {
            addErrorMessage("latitude", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getLongitude()) {
            addErrorMessage("longitude", new ValidationUtil.Required(), errors);
        } else if (dto.getLongitude().isEmpty()) {
            addErrorMessage("longitude", new ValidationUtil.NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateUpdateDTO(EventUpdateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateUpdateDTO(dto);

        if (null == dto.getEventId()) {
            addErrorMessage("eventId", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null != dto.getConflictId() && dto.getConflictId().isEmpty()) {
            addErrorMessage("conflictId", new ValidationUtil.NotNull(), errors);
        }

        if (null != dto.getDate() && dto.getDate().isEmpty()) {
            addErrorMessage("date", new ValidationUtil.NotNull(), errors);
        }

        if (null != dto.getLatitude() && dto.getLatitude().isEmpty()) {
            addErrorMessage("latitude", new ValidationUtil.NotNull(), errors);
        }

        if (null != dto.getLongitude() && dto.getLongitude().isEmpty()) {
            addErrorMessage("longitude", new ValidationUtil.NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
