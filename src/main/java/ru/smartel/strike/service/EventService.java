package ru.smartel.strike.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.event.EventRequestDTO;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.dto.response.event.EventListWrapperDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;

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
    EventDetailDTO create(EventRequestDTO data, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isEventAuthor(#eventId)")
    EventDetailDTO update(Integer eventId, EventRequestDTO data, Integer userId, Locale locale) throws BusinessRuleValidationException, DTOValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    void delete(Integer eventId);
}
