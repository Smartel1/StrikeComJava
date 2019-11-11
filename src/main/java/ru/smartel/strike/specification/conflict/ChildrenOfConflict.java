package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Direct children of conflict
 */
public class ChildrenOfConflict implements Specification<Conflict> {

    private long parentId;

    public ChildrenOfConflict(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("parent").get("id"), parentId);
    }
}
