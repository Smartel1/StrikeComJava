package ru.smartel.strike.service;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.dto.request.news.NewsListRequestDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;

public interface FiltersTransformer {
    Specification<Event> toSpecification(EventListRequestDTO.FiltersBag filters, Integer userId);
    Specification<News> toSpecification(NewsListRequestDTO.FiltersBag filters, Integer userId);
}
