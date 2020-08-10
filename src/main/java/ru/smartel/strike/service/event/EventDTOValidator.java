package ru.smartel.strike.service.event;

import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.service.validation.BasePostDTOValidator;

import java.util.List;
import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Component
public class EventDTOValidator extends BasePostDTOValidator {

    public void validateListQueryDTO(EventListRequestDTO dto) {
        Map<String, List<String>> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
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

        throwIfErrorsExist(errors);
    }

    public void validateStoreDTO(EventCreateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", notNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflictId", required(), errors);
        } else if (dto.getConflictId().isEmpty()) {
            addErrorMessage("conflictId", notNull(), errors);
        }

        if (null == dto.getLatitude()) {
            addErrorMessage("latitude", required(), errors);
        } else if (dto.getLatitude().isEmpty()) {
            addErrorMessage("latitude", notNull(), errors);
        }

        if (null == dto.getLongitude()) {
            addErrorMessage("longitude", required(), errors);
        } else if (dto.getLongitude().isEmpty()) {
            addErrorMessage("longitude", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    public void validateUpdateDTO(EventUpdateRequestDTO dto) {
        Map<String, List<String>> errors = super.validateUpdateDTO(dto);

        if (null == dto.getEventId()) {
            addErrorMessage("eventId", notNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", notNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", notNull(), errors);
        }

        if (null != dto.getConflictId() && dto.getConflictId().isEmpty()) {
            addErrorMessage("conflictId", notNull(), errors);
        }

        if (null != dto.getDate() && dto.getDate().isEmpty()) {
            addErrorMessage("date", notNull(), errors);
        }

        if (null != dto.getLatitude() && dto.getLatitude().isEmpty()) {
            addErrorMessage("latitude", notNull(), errors);
        }

        if (null != dto.getLongitude() && dto.getLongitude().isEmpty()) {
            addErrorMessage("longitude", notNull(), errors);
        }

        throwIfErrorsExist(errors);
    }
}
