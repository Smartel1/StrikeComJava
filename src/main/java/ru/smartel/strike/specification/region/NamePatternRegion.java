package ru.smartel.strike.specification.region;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.reference.Region;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NamePatternRegion implements Specification<Region> {
    private String name;

    public NamePatternRegion(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
