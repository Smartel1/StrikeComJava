package ru.smartel.strike.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventFiltersDTO;
import ru.smartel.strike.dto.request.news.NewsFiltersDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.repository.ConflictRepository;
import ru.smartel.strike.service.FiltersTransformer;
import ru.smartel.strike.specification.event.*;
import ru.smartel.strike.specification.news.*;

import java.util.List;

//TODO refactor
@Service
public class FiltersTransformerImpl implements FiltersTransformer {

    private ConflictRepository conflictRepository;

    public FiltersTransformerImpl(ConflictRepository conflictRepository) {
        this.conflictRepository = conflictRepository;
    }

    @Override
    public Specification<Event> toSpecification(EventFiltersDTO filters, Integer userId) {
        if (null == filters) return null;

        //Empty specification
        Specification<Event> result = null;

        if (null != filters.getPublished()) result = and(result, new PublishedEvent(filters.getPublished()));
        if (null != filters.getDateFrom()) result = and(result, new AfterDateEvent(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = and(result, new BeforeDateEvent(filters.getDateTo()));
        if (null != filters.getEventStatusIds()) result = and(result, new MatchStatusesEvent(filters.getEventStatusIds()));
        if (null != filters.getEventTypeIds()) result = and(result, new MatchTypesEvent(filters.getEventTypeIds()));
        if (null != filters.getTagId()) result = and(result, new HasTagEvent(filters.getTagId()));
        if (null != filters.getConflictIds()) {
            //additional query to find ids of parent events of conflicts with given ids
            List<Integer> parentEventIds = conflictRepository.findAllByIdGetParentEventId(filters.getConflictIds());
            result = and(result, new BelongToConflictsEvent(filters.getConflictIds()).or(new WithIdsEvents(parentEventIds)));
        }
        if (null != filters.getFavourites() && null != userId) result = and(result, new FavouriteEvent(userId));
        if (null != filters.getContainsContent()) result = and(result, new WithContentEvent(filters.getContainsContent()));
        if (null != filters.getCountryIds()) result = and(result, new CountryEvent(filters.getCountryIds()));
        if (null != filters.getRegionIds()) result = and(result, new RegionEvent(filters.getRegionIds()));
        if (null != filters.getNear()) result = and(result, new NearCoordinateEvent(filters.getNear().getLat(), filters.getNear().getLng(), filters.getNear().getRadius()));

        return result;
    }

    @Override
    public Specification<News> toSpecification(NewsFiltersDTO filters, Integer userId) {
        if (null == filters) return null;

        //Empty specification
        Specification<News> result = null;

        if (null != filters.getPublished()) result = and(result, new PublishedNews(filters.getPublished()));
        if (null != filters.getDateFrom()) result = and(result, new AfterDateNews(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = and(result, new BeforeDateNews(filters.getDateTo()));
        if (null != filters.getFavourites() && null != userId) result = and(result, new FavouriteNews(userId));
        if (null != filters.getTagId()) result = and(result, new HasTagNews(filters.getTagId()));

        return result;
    }

    private <T> Specification<T> and(Specification<T> s1, Specification<T> s2) {
        if (null == s1) return s2;
        return s1.and(s2);
    }
}
