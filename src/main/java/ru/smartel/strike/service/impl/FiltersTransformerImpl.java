package ru.smartel.strike.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.smartel.strike.dto.request.event.EventListRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.EventFiltersTransformer;
import ru.smartel.strike.specification.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

@Service
public class FiltersTransformerImpl implements EventFiltersTransformer {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Specification> toSpecifications(EventListRequestDTO.FiltersBag filters, User user) {
        List<Specification> result = new LinkedList<>();

        if (null != filters.getPublished()) result.add(new PublishedEvent(filters.getPublished()));
        if (null != filters.getDateFrom()) result.add(new AfterDateEvent(filters.getDateFrom()));
        if (null != filters.getDateTo()) result.add(new BeforeDateEvent(filters.getDateTo()));
        if (null != filters.getEventStatusIds()) result.add(new MatchStatusesEvent(filters.getEventStatusIds()));
        if (null != filters.getEventTypeIds()) result.add(new MatchTypesEvent(filters.getEventTypeIds()));
        if (null != filters.getTagId()) result.add(new HasTagEvent(filters.getTagId()));
        if (null != filters.getConflictIds()) {
            //additional query to find ids of parent events of conflicts with given ids
            List<Integer> parentEventIds = entityManager
                    .createQuery("select parentEvent.id from Conflict where id in :ids")
                    .setParameter("ids", filters.getConflictIds())
                    .getResultList();
            result.add(new BelongToConflictsEvent(filters.getConflictIds()).or(new WithIdsEvents(parentEventIds)));
        }
        if (null != filters.getFavourites() && null != user) result.add(new FavouriteEvent(user.getId()));
        if (null != filters.getContainsContent()) result.add(new WithContentEvent(filters.getContainsContent()));
        if (null != filters.getCountryIds()) result.add(new CountryEvent(filters.getCountryIds()));
        if (null != filters.getRegionIds()) result.add(new RegionEvent(filters.getRegionIds()));
        if (null != filters.getNear()) result.add(new NearCoordinateEvent(filters.getNear().getLat(), filters.getNear().getLng(), filters.getNear().getRadius()));

        return result;
    }
}
