package ru.smartel.strike.specification.news;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * User's favourite news
 */
public class FavouriteNews implements Specification<News> {
    private int userId;

    public FavouriteNews(int userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(root.join("likedUsers").get("id")).value(userId);
    }
}
