package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Conflicts with results
 */
public class MatchResultsConflict implements Specification<Conflict> {
    private List<Long> resultIds;

    public MatchResultsConflict(List<Long> resultIds) {
        this.resultIds = resultIds;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("result").get("id")).value(resultIds);
    }
}
