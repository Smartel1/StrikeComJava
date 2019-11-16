package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Ancestors of conflict (not only direct)
 */
public class AncestorsOfConflict implements Specification<Conflict> {

    private long lft;
    private long rgt;

    public AncestorsOfConflict(long lft, long rgt) {
        this.lft = lft;
        this.rgt = rgt;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.and(
                cb.greaterThan(root.get("rgt"), rgt),
                cb.lessThan(root.get("lft"), lft)
        );
    }
}
