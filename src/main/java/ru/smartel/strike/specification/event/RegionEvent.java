package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events from region
 */
public class RegionEvent implements Specification<Event> {
    private List<Integer> regionIds;

    public RegionEvent(List<Integer> regionIds) {
        this.regionIds = regionIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(
                root
                        .join("locality")
                        .get("region")
                        .get("id")
        ).value(regionIds);
    }
}
