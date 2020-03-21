package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * News with tags
 */
public class NewsWithAnyTags implements Specification<News> {
    private List<String> tags;

    public NewsWithAnyTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var in = cb.in(root.join("tags").get("name"));
        tags.forEach(in::value);
        return in;
    }
}
