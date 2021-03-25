package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Conflicts with industries
 */
public class MatchIndustriesConflict implements Specification<Conflict> {
    private final List<Long> industryIds;

    public MatchIndustriesConflict(List<Long> industryIds) {
        this.industryIds = industryIds;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("industry").get("id")).value(industryIds);
    }
}
