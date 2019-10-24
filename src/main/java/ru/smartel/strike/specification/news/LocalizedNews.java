package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;
import ru.smartel.strike.service.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Only news with localized title and content
 */
public class LocalizedNews implements Specification<News> {
    private Locale locale;

    public LocalizedNews(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (!locale.equals(Locale.ALL)) {
            return cb.and(
                    cb.isNotNull(root.get("title" + locale.getPascalCase())),
                    cb.isNotNull(root.get("content" + locale.getPascalCase()))
            );
        }
        return null;
    }
}
