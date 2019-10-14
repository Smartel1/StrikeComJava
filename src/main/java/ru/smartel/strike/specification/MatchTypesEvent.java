package ru.smartel.strike.specification;

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
public class MatchTypesEvent implements Specification<Event> {
    private List<Integer> typeIds;

    public MatchTypesEvent(List<Integer> typeIds) {
        this.typeIds = typeIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.get("type").get("id")).value(typeIds);
    }
}
