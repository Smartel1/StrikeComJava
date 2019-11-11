package ru.smartel.strike.service.event;

import ru.smartel.strike.dto.request.event.EventCreateRequestDTO;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.service.Locale;

public interface EventService {

    Long getNonPublishedCount();

    ListWrapperDTO<EventListDTO> list(EventListRequestDTO dto);

    EventDetailDTO incrementViewsAndGet(Long eventId, Locale locale, boolean withRelatives);

    void setFavourite(Long eventId, long userId, boolean isFavourite);

    EventDetailDTO create(EventCreateRequestDTO dto);

    EventDetailDTO update(EventUpdateRequestDTO dto);

    void delete(Long eventId);
}
