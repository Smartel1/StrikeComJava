package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events with types
 */
public class MatchIndustriesEvent implements Specification<Event> {
    private final List<Long> industryIds;

    public MatchIndustriesEvent(List<Long> industryIds) {
        this.industryIds = industryIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("conflict").get("industry").get("id")).value(industryIds);
    }
}
