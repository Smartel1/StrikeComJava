package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.*;

import static java.util.Objects.isNull;

/**
 * Conflicts which have specified text in title or which have events containing this text in contents/titles
 */
public class TextMentionConflict implements Specification<Conflict> {
    private final String desiredContent;

    public TextMentionConflict(String desiredContent) {
        this.desiredContent = desiredContent;
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (isNull(desiredContent) || desiredContent.isBlank()) return null;

        var desiredContentQuery = "%" + desiredContent.toLowerCase() + "%";
        Join<Conflict, Event> join = root.join("events", JoinType.LEFT);
        return cb.or(
                cb.like(cb.lower(root.get("titleRu")), desiredContentQuery),
                cb.like(cb.lower(root.get("titleEn")), desiredContentQuery),
                cb.like(cb.lower(root.get("titleEs")), desiredContentQuery),
                cb.like(cb.lower(root.get("titleDe")), desiredContentQuery),
                cb.like(cb.lower(root.get("companyName")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("titleRu")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("titleEn")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("titleEs")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("titleDe")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("contentRu")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("contentEn")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("contentEs")), desiredContentQuery),
                cb.like(cb.lower(join.get("post").get("contentDe")), desiredContentQuery)
        );
    }
}
