package ru.smartel.strike.service.filters;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.smartel.strike.dto.request.conflict.ConflictFiltersDTO;
import ru.smartel.strike.dto.request.event.EventFiltersDTO;
import ru.smartel.strike.dto.request.news.NewsFiltersDTO;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.repository.conflict.ConflictRepository;
import ru.smartel.strike.specification.conflict.AfterDateConflict;
import ru.smartel.strike.specification.conflict.AncestorsOfConflict;
import ru.smartel.strike.specification.conflict.BeforeDateConflict;
import ru.smartel.strike.specification.conflict.ChildrenOfConflict;
import ru.smartel.strike.specification.conflict.MatchReasonsConflict;
import ru.smartel.strike.specification.conflict.MatchResultsConflict;
import ru.smartel.strike.specification.conflict.NearCoordinateConflict;
import ru.smartel.strike.specification.conflict.TextMentionConflict;
import ru.smartel.strike.specification.event.AfterDateEvent;
import ru.smartel.strike.specification.event.BeforeDateEvent;
import ru.smartel.strike.specification.event.BelongToConflictsEvent;
import ru.smartel.strike.specification.event.CountryEvent;
import ru.smartel.strike.specification.event.FavouriteEvent;
import ru.smartel.strike.specification.event.HasTagEvent;
import ru.smartel.strike.specification.event.MatchStatusesEvent;
import ru.smartel.strike.specification.event.MatchTypesEvent;
import ru.smartel.strike.specification.event.NearCoordinateEvent;
import ru.smartel.strike.specification.event.PublishedEvent;
import ru.smartel.strike.specification.event.RegionEvent;
import ru.smartel.strike.specification.event.WithContentEvent;
import ru.smartel.strike.specification.event.WithIdsEvents;
import ru.smartel.strike.specification.news.AfterDateNews;
import ru.smartel.strike.specification.news.BeforeDateNews;
import ru.smartel.strike.specification.news.FavouriteNews;
import ru.smartel.strike.specification.news.HasTagNews;
import ru.smartel.strike.specification.news.PublishedNews;
import ru.smartel.strike.specification.news.WithContentNews;

import java.util.List;

@Component
public class FiltersTransformerImpl implements FiltersTransformer {

    private ConflictRepository conflictRepository;

    public FiltersTransformerImpl(ConflictRepository conflictRepository) {
        this.conflictRepository = conflictRepository;
    }

    @Override
    public Specification<Event> toSpecification(EventFiltersDTO filters, Long userId) {
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
            List<Long> parentEventIds = conflictRepository.findAllByIdGetParentEventId(filters.getConflictIds());
            result = result.and(new BelongToConflictsEvent(filters.getConflictIds()).or(new WithIdsEvents(parentEventIds)));
        }
        if (null != filters.getFavourites() && filters.getFavourites() && null != userId) {
            result = result.and(new FavouriteEvent(userId));
        }
        if (null != filters.getFulltext()) result = result.and(new WithContentEvent(filters.getFulltext()));
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
    public Specification<News> toSpecification(NewsFiltersDTO filters, Long userId) {
        Specification<News> result = emptySpecification();

        if (null == filters) return result;

        if (null != filters.getPublished()) result = result.and(new PublishedNews(filters.getPublished()));
        if (null != filters.getDateFrom()) result = result.and(new AfterDateNews(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = result.and(new BeforeDateNews(filters.getDateTo()));
        if (null != filters.getFavourites() && filters.getFavourites() && null != userId) result = result.and(new FavouriteNews(userId));
        if (null != filters.getTagId()) result = result.and(new HasTagNews(filters.getTagId()));
        if (null != filters.getFulltext()) result = result.and(new WithContentNews(filters.getFulltext()));

        return result;
    }

    @Override
    public Specification<Conflict> toSpecification(ConflictFiltersDTO filters, Long userId) {
        Specification<Conflict> result = emptySpecification();

        if (null == filters) return result;

        if (null != filters.getDateFrom()) result = result.and(new AfterDateConflict(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = result.and(new BeforeDateConflict(filters.getDateTo()));
        if (null != filters.getFulltext()) result = result.and(new TextMentionConflict(filters.getFulltext()));
        if (null != filters.getConflictResultIds()) result = result.and(new MatchResultsConflict(filters.getConflictResultIds()));
        if (null != filters.getConflictReasonIds()) result = result.and(new MatchReasonsConflict(filters.getConflictReasonIds()));
        if (null != filters.getAncestorsOf()) {
            Conflict descendant = conflictRepository.findById(filters.getAncestorsOf()).orElse(null);
            if (null == descendant) {
                result = result.and(falseSpecification());
            } else {
                result = result.and(new AncestorsOfConflict(descendant.getTreeLeft(), descendant.getTreeRight()));
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
