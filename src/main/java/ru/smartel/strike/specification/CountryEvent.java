package ru.smartel.strike.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Events from country
 */
public class CountryEvent implements Specification<Event> {
    private List<Integer> countryIds;

    public CountryEvent(List<Integer> countryIds) {
        this.countryIds = countryIds;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.in(
                root
                        .join("locality")
                        .join("region")
                        .get("country")
                        .get("id")
        ).value(countryIds);
    }
}
