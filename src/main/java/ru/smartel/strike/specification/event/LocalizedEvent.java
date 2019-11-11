package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.service.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Only events with localized title and content
 */
public class LocalizedEvent implements Specification<Event> {
    private Locale locale;

    public LocalizedEvent(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (!locale.equals(Locale.ALL)) {
            return cb.and(
                    cb.isNotNull(root.get("post").get("title" + locale.getPascalCase())),
                    cb.isNotNull(root.get("post").get("content" + locale.getPascalCase()))
            );
        }
        return null;
    }
}
