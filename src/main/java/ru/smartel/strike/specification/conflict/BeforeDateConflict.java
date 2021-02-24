package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Conflicts before specified date only
 */
public class BeforeDateConflict implements Specification<Conflict> {
    private final int dateTo;

    public BeforeDateConflict(int dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Conflict, Event> join = root.join("events", JoinType.LEFT);
        return cb.or(
                cb.lessThanOrEqualTo(join.get("post").get("date"),
                        LocalDateTime.ofEpochSecond(dateTo, 0, ZoneOffset.UTC)));
    }
}
