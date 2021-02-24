package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Conflicts after specified date only
 */
public class AfterDateConflict implements Specification<Conflict> {
    private final int dateFrom;

    public AfterDateConflict(int dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Conflict, Event> join = root.join("events", JoinType.LEFT);
        return cb.or(
                cb.greaterThanOrEqualTo(join.get("post").get("date"),
                LocalDateTime.ofEpochSecond(dateFrom, 0, ZoneOffset.UTC)));
    }
}
