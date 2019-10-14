package ru.smartel.strike.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.smartel.strike.service.FiltersTransformer;
import ru.smartel.strike.specification.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class FiltersTransformerImpl implements FiltersTransformer {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Specification> toSpecifications(ObjectNode jsonFilters, Integer userId) {
        List<Specification> result = new LinkedList<>();
        JsonMapper mapper = new JsonMapper();

        Map<String, Object> filters = mapper.convertValue(jsonFilters, Map.class);

        filters.forEach((filterName, filterValue) -> result.add(getSpecificationByFilter(filterName, filterValue, userId)));

        return result;
    }

    private Specification getSpecificationByFilter(String filterName, Object filterValue, Integer userId) {
        switch (filterName) {
            case "published": return new PublishedEvent((boolean)filterValue);
            case "date_from": return new AfterDateEvent((int)filterValue);
            case "date_to": return new BeforeDateEvent((int)filterValue);
            case "event_status_ids": return new MatchStatusesEvent((List<Integer>)filterValue);
            case "event_type_ids": return new MatchTypesEvent((List<Integer>)filterValue);
            case "tag_id": return new HasTagEvent((int)filterValue);
            case "conflict_ids": {
                List<Integer> conflictIds = (List<Integer>) filterValue;
                //additional query to find ids of parent events of conflicts with given ids
                List<Integer> parentEventIds = entityManager
                        .createQuery("select parentEvent.id from Conflict where id in :ids")
                        .setParameter("ids", conflictIds)
                        .getResultList();

                return new BelongToConflictsEvent(conflictIds).or(new WithIdsEvents(parentEventIds));
            }
            case "favourites": return new FavouriteEvent(userId);
            case "contains_content": return new WithContentEvent((String)filterValue);
            case "country_ids": return new CountryEvent((List<Integer>) filterValue);
            case "region_ids": return new RegionEvent((List<Integer>) filterValue);
            case "near": {
                Map<String, Number> values = (Map<String, Number>)filterValue;
                return new NearCoordinateEvent(values.get("lat").doubleValue(), values.get("lng").doubleValue(), values.get("radius").doubleValue());
            }
            default: throw new IllegalStateException("filter " + filterName + "is not recognized");
        }
    }
}
