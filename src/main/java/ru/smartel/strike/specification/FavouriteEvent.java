package ru.smartel.strike.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User's favourite events
 */
public class FavouriteEvent implements Specification<Event> {
    private int userId;

    public FavouriteEvent(int userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.join("likedUsers").get("id")).value(userId);
    }
}
