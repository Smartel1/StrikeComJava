package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Only events user may see
 */
public class ByRolesEvent implements Specification<Event> {
    private User user;

    public ByRolesEvent(User user) {
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (null == user
                || !user.getRolesAsList().contains(User.ROLE_MODERATOR)
                && !user.getRolesAsList().contains(User.ROLE_ADMIN)) {
            return cb.equal(root.get("published"), true);
        }
        return null;
    }
}
