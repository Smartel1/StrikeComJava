package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.security.token.UserPrincipal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Only events user may see
 */
public class ByRolesEvent implements Specification<Event> {
    private UserPrincipal user;

    public ByRolesEvent(UserPrincipal user) {
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (null == user
                || !user.getRoles().contains(User.ROLE_MODERATOR)
                && !user.getRoles().contains(User.ROLE_ADMIN)) {
            return cb.equal(root.get("post").get("published"), true);
        }
        return null;
    }
}
