package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * News with tag
 */
public class HasTagNews implements Specification<News> {
    private int tagId;

    public HasTagNews(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.join("tags").get("id"), tagId);
    }
}
