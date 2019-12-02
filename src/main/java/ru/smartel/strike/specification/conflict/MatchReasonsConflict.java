package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Conflicts with reasons
 */
public class MatchReasonsConflict implements Specification<Conflict> {
    private List<Long> reasonIds;

    public MatchReasonsConflict(List<Long> reasonIds) {
        this.reasonIds = reasonIds;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("reason").get("id")).value(reasonIds);
    }
}
