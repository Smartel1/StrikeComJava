package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * News with specified content part
 */
public class WithContentNews implements Specification<News> {
    private String desiredContent;

    public WithContentNews(String desiredContent) {
        this.desiredContent = "%" + desiredContent.toLowerCase() + "%";
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.or(
                cb.like(cb.lower(root.get("post").get("titleRu")), desiredContent),
                cb.like(cb.lower(root.get("post").get("titleEn")), desiredContent),
                cb.like(cb.lower(root.get("post").get("titleEs")), desiredContent),
                cb.like(cb.lower(root.get("post").get("titleDe")), desiredContent),
                cb.like(cb.lower(root.get("post").get("contentRu")), desiredContent),
                cb.like(cb.lower(root.get("post").get("contentEn")), desiredContent),
                cb.like(cb.lower(root.get("post").get("contentEs")), desiredContent),
                cb.like(cb.lower(root.get("post").get("contentDe")), desiredContent)
        );
    }
}
