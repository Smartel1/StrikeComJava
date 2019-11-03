package ru.smartel.strike.service.event;

import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.validation.BasePostDTOValidator;
import ru.smartel.strike.util.ValidationUtil;

import java.util.Map;

import static ru.smartel.strike.util.ValidationUtil.*;

@Service
public class EventDTOValidatorImpl extends BasePostDTOValidator implements EventDTOValidator {

    @Override
    public void validateListQueryDTO(EventListRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateListQueryDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new NotNull(), errors);
        }

        throwIfErrorsExist(errors);
    }

    @Override
    public void validateStoreDTO(EventCreateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateStoreDTO(dto);

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getConflictId()) {
            addErrorMessage("conflict_id", new ValidationUtil.Required(), errors);
        } else if (dto.getConflictId().isEmpty()) {
            addErrorMessage("conflict_id", new ValidationUtil.NotNull(), errors);
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
    public void validateUpdateDTO(EventUpdateRequestDTO dto) throws DTOValidationException {
        Map<String, String> errors = super.validateUpdateDTO(dto);

        if (null == dto.getEventId()) {
            addErrorMessage("event_id", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getLocale()) {
            addErrorMessage("locale", new ValidationUtil.NotNull(), errors);
        }

        if (null == dto.getUser()) {
            addErrorMessage("user", new ValidationUtil.NotNull(), errors);
        }

        if (null != dto.getConflictId() && dto.getConflictId().isEmpty()) {
            addErrorMessage("conflict_id", new ValidationUtil.NotNull(), errors);
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
