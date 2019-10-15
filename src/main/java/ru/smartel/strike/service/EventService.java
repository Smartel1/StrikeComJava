package ru.smartel.strike.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.EventListRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public interface EventService {

    @PreAuthorize("permitAll()")
    EventListWrapperDTO index(EventListRequestDTO.FiltersBag filters, int perPage, int page, Locale locale, User user);

    @PreAuthorize("permitAll()")
    EventDetailDTO incrementViewsAndGet(Integer eventId, Locale locale, boolean withRelatives);

    @PreAuthorize("isFullyAuthenticated()")
    void setFavourite(Integer eventId, Integer userId, boolean isFavourite);

    @PreAuthorize("isFullyAuthenticated()")
    EventDetailDTO create(JsonNode data, Integer userId, Locale locale) throws BusinessRuleValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isEventAuthor(#eventId)")
    EventDetailDTO update(Integer eventId, JsonNode data, Integer userId, Locale locale) throws BusinessRuleValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    void delete(Integer eventId);
}
