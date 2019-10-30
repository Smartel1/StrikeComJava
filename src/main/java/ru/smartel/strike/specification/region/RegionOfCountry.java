package ru.smartel.strike.specification.region;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.reference.Region;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RegionOfCountry implements Specification<Region> {
    private Integer countryId;

    public RegionOfCountry(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //if countryId is null - dont restrict
        if (null == countryId) return criteriaBuilder.and();
        return criteriaBuilder.equal(root.get("country").get("id"), countryId);
    }
}
