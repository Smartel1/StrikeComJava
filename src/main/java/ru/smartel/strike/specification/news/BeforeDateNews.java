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
 * Only news before specified date
 */
public class BeforeDateNews implements Specification<News> {
    private int dateTo;

    public BeforeDateNews(int dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThanOrEqualTo(root.get("post").get("date"),
                LocalDateTime.ofEpochSecond(dateTo, 0, ZoneOffset.UTC));
    }
}
