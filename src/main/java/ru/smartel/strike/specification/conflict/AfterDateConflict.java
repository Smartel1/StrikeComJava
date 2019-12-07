package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Conflicts after specified date only
 */
public class AfterDateConflict implements Specification<Conflict> {
    private int dateFrom;

    public AfterDateConflict(int dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(root.get("dateTo"),
                LocalDateTime.ofEpochSecond(dateFrom, 0, ZoneOffset.UTC));
    }
}
