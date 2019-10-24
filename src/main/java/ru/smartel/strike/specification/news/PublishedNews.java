package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Only published news
 */
public class PublishedNews implements Specification<News> {
    private boolean published;

    public PublishedNews(boolean published) {
        this.published = published;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get("published"), published);
    }
}
