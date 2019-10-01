package ru.smartel.strike.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smartel.strike.dto.response.event.EventDetailDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.exception.BusinessRuleValidationException;

@Service
@Transactional(rollbackFor = Exception.class)
public interface EventService {

    @PreAuthorize("permitAll()")
    EventDetailDTO getAndIncrementViews(Integer id, Locale locale, boolean withRelatives);

    @PreAuthorize("isFullyAuthenticated()")
    EventDetailDTO create(JsonNode data, User user, Locale locale) throws BusinessRuleValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') or isEventAuthor(#id)")
    EventDetailDTO update(Integer id, JsonNode data, User user, Locale locale) throws BusinessRuleValidationException;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    void delete(Integer id);
}
