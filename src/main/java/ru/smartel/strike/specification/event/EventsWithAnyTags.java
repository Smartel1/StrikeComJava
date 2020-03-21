package ru.smartel.strike.specification.event;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

/**
 * Events with tags
 */
public class EventsWithAnyTags implements Specification<Event> {
    private List<String> tags;

    public EventsWithAnyTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var in = cb.in(root.join("tags").get("name"));
        tags.forEach(in::value);
        return in;
    }
}
