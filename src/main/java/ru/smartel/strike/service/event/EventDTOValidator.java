package ru.smartel.strike.service.event;

import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;

public interface EventDTOValidator {
    void validateListQueryDTO(EventListRequestDTO dto);
    void validateStoreDTO(EventCreateRequestDTO dto);
    void validateUpdateDTO(EventUpdateRequestDTO dto);
}
