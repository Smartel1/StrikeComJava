package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.service.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Conflicts with localized title
 */
public class LocalizedConflict implements Specification<Conflict> {
    private Locale locale;

    public LocalizedConflict(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (!locale.equals(Locale.ALL)) {
            return cb.isNotNull(root.get("title" + locale.getPascalCase()));
        }
        return null;
    }
}
