package ru.smartel.strike.service;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.entity.User;

import java.util.List;

public interface EventFiltersTransformer {
    List<Specification> toSpecifications(EventListRequestDTO.FiltersBag filters, User user);
}
