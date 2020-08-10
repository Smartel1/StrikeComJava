package ru.smartel.strike.specification.conflict;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Conflicts with main types
 */
public class MatchMainTypesConflict implements Specification<Conflict> {
    private final List<Long> mainTypeIds;

    public MatchMainTypesConflict(List<String> mainTypeIds) {
        this.mainTypeIds = mainTypeIds.stream()
                .map(typeIdString -> {
                    if (typeIdString.equals("null")) {
                        return null;
                    } else {
                        return Long.parseLong(typeIdString);
                    }
                }).collect(toList());
    }

    @Override
    public Predicate toPredicate(Root<Conflict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        boolean orNull = mainTypeIds.contains(null);
        var inPredicate = cb.in(root.get("mainType").get("id"))
                .value(mainTypeIds.stream().filter(Objects::nonNull).collect(toList()));
        if (orNull) {
            var isNullPredicate = cb.isNull(root.get("mainType").get("id"));
            return cb.or(inPredicate, isNullPredicate);
        }
        return inPredicate;
    }
}
