package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Only events before specified date
 */
public class BeforeDateEvent implements Specification<Event> {
    private int dateTo;

    public BeforeDateEvent(int dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(root.get("post").get("date"),
                LocalDateTime.ofEpochSecond(dateTo, 0, ZoneOffset.UTC));
    }
}
