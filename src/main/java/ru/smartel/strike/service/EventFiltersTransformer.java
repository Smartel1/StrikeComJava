package ru.smartel.strike.service;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;

public interface EventFiltersTransformer {
    Specification<Event> toSpecification(EventListRequestDTO.FiltersBag filters, User user);
}
