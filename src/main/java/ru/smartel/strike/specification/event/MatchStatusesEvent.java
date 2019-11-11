package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events with statuses
 */
public class MatchStatusesEvent implements Specification<Event> {
    private List<Long> statusIds;

    public MatchStatusesEvent(List<Long> statusIds) {
        this.statusIds = statusIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("status").get("id")).value(statusIds);
    }
}
