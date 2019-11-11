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
 * Conflicts before specified date only
 */
public class BeforeDateConflict implements Specification<Conflict> {
    private int dateTo;

    public BeforeDateConflict(int dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(root.get("dateTo"),
                LocalDateTime.ofEpochSecond(dateTo, 0, ZoneOffset.UTC));
    }
}
