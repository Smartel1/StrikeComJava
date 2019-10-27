package ru.smartel.strike.service;

import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;

public interface EventService {

    ListWrapperDTO<EventListDTO> list(EventListRequestDTO dto, Locale locale, User user)
            throws DTOValidationException;

    EventDetailDTO incrementViewsAndGet(Integer eventId, Locale locale, boolean withRelatives);

    void setFavourite(Integer eventId, int userId, boolean isFavourite);

    EventDetailDTO create(EventRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    EventDetailDTO update(Integer eventId, EventRequestDTO dto, Integer userId, Locale locale)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer eventId) throws BusinessRuleValidationException;
}
