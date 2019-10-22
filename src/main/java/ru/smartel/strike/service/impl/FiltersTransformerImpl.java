package ru.smartel.strike.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.EventFiltersTransformer;
import ru.smartel.strike.specification.event.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class FiltersTransformerImpl implements EventFiltersTransformer {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Specification toSpecification(EventListRequestDTO.FiltersBag filters, User user) {
        //Empty specification
        Specification<Event> result = null;

        if (null == filters) return result;

        if (null != filters.getPublished()) result = and(result, new PublishedEvent(filters.getPublished()));
        if (null != filters.getDateFrom()) result = and(result, new AfterDateEvent(filters.getDateFrom()));
        if (null != filters.getDateTo()) result = and(result, new BeforeDateEvent(filters.getDateTo()));
        if (null != filters.getEventStatusIds()) result = and(result, new MatchStatusesEvent(filters.getEventStatusIds()));
        if (null != filters.getEventTypeIds()) result = and(result, new MatchTypesEvent(filters.getEventTypeIds()));
        if (null != filters.getTagId()) result = and(result, new HasTagEvent(filters.getTagId()));
        if (null != filters.getConflictIds()) {
            //additional query to find ids of parent events of conflicts with given ids
            List<Integer> parentEventIds = entityManager
                    .createQuery("select parentEvent.id from Conflict where id in :ids")
                    .setParameter("ids", filters.getConflictIds())
                    .getResultList();
            result = and(result, new BelongToConflictsEvent(filters.getConflictIds()).or(new WithIdsEvents(parentEventIds)));
        }
        if (null != filters.getFavourites() && null != user) result = and(result, new FavouriteEvent(user.getId()));
        if (null != filters.getContainsContent()) result = and(result, new WithContentEvent(filters.getContainsContent()));
        if (null != filters.getCountryIds()) result = and(result, new CountryEvent(filters.getCountryIds()));
        if (null != filters.getRegionIds()) result = and(result, new RegionEvent(filters.getRegionIds()));
        if (null != filters.getNear()) result = and(result, new NearCoordinateEvent(filters.getNear().getLat(), filters.getNear().getLng(), filters.getNear().getRadius()));

        return result;
    }

    private Specification<Event> and(Specification<Event> s1, Specification<Event> s2) {
        if (null == s1) return s2;
        return s1.and(s2);
    };

}
