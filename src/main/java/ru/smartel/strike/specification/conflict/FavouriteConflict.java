package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User's favourite conflicts
 */
public class FavouriteConflict implements Specification<Conflict> {
    private final long userId;

    public FavouriteConflict(long userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.join("likedUsers").get("id")).value(userId);
    }
}
