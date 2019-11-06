package ru.smartel.strike.service.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.conflict.ConflictFiltersDTO;
import ru.smartel.strike.dto.request.event.EventFiltersDTO;
import ru.smartel.strike.dto.request.news.NewsFiltersDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.service.filters.FiltersTransformer;
import ru.smartel.strike.specification.conflict.*;
import ru.smartel.strike.specification.event.*;
import ru.smartel.strike.specification.news.*;

import java.util.List;

@Service
public class FiltersTransformerImpl implements FiltersTransformer {

    private ConflictRepository conflictRepository;

    public FiltersTransformerImpl(ConflictRepository conflictRepository) {
        this.conflictRepository = conflictRepository;
    }

    @Override
    public Specification<Event> toSpecification(EventFiltersDTO filters, Integer userId) {
        Specification<Event> result = emptySpecification();

        if (null == filters) return result;

        if (null != filters.getPublished()) result = result.and(new PublishedEvent(filters.getPublished()));
        if (null != filters.getDateFrom()) result = result.and(new AfterDateEvent(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = result.and(new BeforeDateEvent(filters.getDateTo()));
        if (null != filters.getEventStatusIds()) result = result.and(new MatchStatusesEvent(filters.getEventStatusIds()));
        if (null != filters.getEventTypeIds()) result = result.and(new MatchTypesEvent(filters.getEventTypeIds()));
        if (null != filters.getTagId()) result = result.and(new HasTagEvent(filters.getTagId()));
        if (null != filters.getConflictIds()) {
            //additional query to find ids of parent events of conflicts with given ids
            List<Integer> parentEventIds = conflictRepository.findAllByIdGetParentEventId(filters.getConflictIds());
            result = result.and(new BelongToConflictsEvent(filters.getConflictIds()).or(new WithIdsEvents(parentEventIds)));
        }
        if (null != filters.getFavourites() && filters.getFavourites() && null != userId) {
            result = result.and(new FavouriteEvent(userId));
        }
        if (null != filters.getContainsContent()) result = result.and(new WithContentEvent(filters.getContainsContent()));
        if (null != filters.getCountryIds()) result = result.and(new CountryEvent(filters.getCountryIds()));
        if (null != filters.getRegionIds()) result = result.and(new RegionEvent(filters.getRegionIds()));
        if (null != filters.getNear())
            result = result.and(
                    new NearCoordinateEvent(
                            filters.getNear().getLat(),
                            filters.getNear().getLng(),
                            filters.getNear().getRadius()
                    )
            );

        return result;
    }

    @Override
    public Specification<News> toSpecification(NewsFiltersDTO filters, Integer userId) {
        Specification<News> result = emptySpecification();

        if (null == filters) return result;

        if (null != filters.getPublished()) result = result.and(new PublishedNews(filters.getPublished()));
        if (null != filters.getDateFrom()) result = result.and(new AfterDateNews(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = result.and(new BeforeDateNews(filters.getDateTo()));
        if (null != filters.getFavourites() && null != userId) result = result.and(new FavouriteNews(userId));
        if (null != filters.getTagId()) result = result.and(new HasTagNews(filters.getTagId()));

        return result;
    }

    @Override
    public Specification<Conflict> toSpecification(ConflictFiltersDTO filters, Integer userId) {
        Specification<Conflict> result = emptySpecification();

        if (null == filters) return result;

        if (null != filters.getDateFrom()) result = result.and(new AfterDateConflict(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = result.and(new BeforeDateConflict(filters.getDateTo()));
        if (null != filters.getConflictResultIds()) result = result.and(new MatchResultsConflict(filters.getConflictResultIds()));
        if (null != filters.getConflictReasonIds()) result = result.and(new MatchReasonsConflict(filters.getConflictReasonIds()));
        if (null != filters.getAncestorsOf()) {
            Conflict descendant = conflictRepository.findById(filters.getAncestorsOf()).orElse(null);
            if (null == descendant) {
                result = result.and(falseSpecification());
            } else {
                result = result.and(new AncestorsOfConflict(descendant.getLft(), descendant.getRgt()));
            }
        }
        if (null != filters.getChildrenOf()) result = result.and(new ChildrenOfConflict(filters.getChildrenOf()));
        if (null != filters.getNear())
            result = result.and(
                    new NearCoordinateConflict(
                            filters.getNear().getLat(),
                            filters.getNear().getLng(),
                            filters.getNear().getRadius()
                    )
            );
        return result;
    }

    /**
     * Create Specification with no restrictions
     */
    private <T> Specification<T> emptySpecification() {
        return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.and();
    }

    /**
     * Create Specification which leads to no results
     */
    private <T> Specification<T> falseSpecification() {
        return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.or();
    }
}
