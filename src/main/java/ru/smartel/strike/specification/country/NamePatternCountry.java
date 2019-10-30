package ru.smartel.strike.specification.country;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.reference.Country;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NamePatternCountry implements Specification<Country> {
    private String name;

    public NamePatternCountry(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + name.toLowerCase() + "%";

        return
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nameRu")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nameEn")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nameEs")), pattern)
                );
    }
}
