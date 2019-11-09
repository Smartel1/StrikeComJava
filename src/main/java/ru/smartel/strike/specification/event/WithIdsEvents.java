package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events with ids
 */
public class WithIdsEvents implements Specification<Event> {
    private List<Long> ids;

    public WithIdsEvents(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        //or parent-events of conflicts with given ids
        return cb.in(root.get("id")).value(ids);
    }
}
