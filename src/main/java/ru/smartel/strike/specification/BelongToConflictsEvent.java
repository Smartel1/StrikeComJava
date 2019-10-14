package ru.smartel.strike.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events belong to conflicts or are parents of conflicts
 */
public class BelongToConflictsEvent implements Specification<Event> {
    private List<Integer> conflictIds;

    public BelongToConflictsEvent(List<Integer> conflictIds) {
        this.conflictIds = conflictIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        //Events which belong to conflicts with given ids
        return cb.in(root.get("conflict").get("id")).value(conflictIds);
    }
}
