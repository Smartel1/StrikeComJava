package ru.smartel.strike.specification.locality;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.reference.Locality;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NamePatternLocality implements Specification<Locality> {
    private String name;

    public NamePatternLocality(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Locality> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
