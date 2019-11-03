package ru.smartel.strike.service.event;

import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface EventService {

    Long getNonPublishedCount();

    ListWrapperDTO<EventListDTO> list(EventListRequestDTO dto)
            throws DTOValidationException;

    EventDetailDTO incrementViewsAndGet(Integer eventId, Locale locale, boolean withRelatives);

    void setFavourite(Integer eventId, int userId, boolean isFavourite);

    EventDetailDTO create(EventCreateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    EventDetailDTO update(EventUpdateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer eventId) throws BusinessRuleValidationException;
}
