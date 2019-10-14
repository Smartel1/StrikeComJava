package ru.smartel.strike.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Only events after specified date
 */
public class AfterDateEvent implements Specification<Event> {
    private int dateFrom;

    public AfterDateEvent(int dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(root.get("date"),
                LocalDateTime.ofEpochSecond(dateFrom, 0, ZoneOffset.UTC));
    }
}
