package ru.smartel.strike.service.filters;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.request.conflict.ConflictFiltersDTO;
import ru.smartel.strike.dto.request.event.EventFiltersDTO;
import ru.smartel.strike.dto.request.news.NewsFiltersDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;

public interface FiltersTransformer {
    Specification<Event> toSpecification(EventFiltersDTO filters, Integer userId);
    Specification<News> toSpecification(NewsFiltersDTO filters, Integer userId);
    Specification<Conflict> toSpecification(ConflictFiltersDTO filters, Integer userId);
}
