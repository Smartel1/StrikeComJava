package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Only news after specified date
 */
public class AfterDateNews implements Specification<News> {
    private int dateFrom;

    public AfterDateNews(int dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(root.get("post").get("date"),
                LocalDateTime.ofEpochSecond(dateFrom, 0, ZoneOffset.UTC));
    }
}
