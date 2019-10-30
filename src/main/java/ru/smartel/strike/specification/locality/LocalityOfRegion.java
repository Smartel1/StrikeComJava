package ru.smartel.strike.specification.locality;

import org.springframework.data.jpa.domain.Specification;
import ru.smartel.strike.entity.reference.Locality;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LocalityOfRegion implements Specification<Locality> {
    private Integer regionId;

    public LocalityOfRegion(Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public Predicate toPredicate(Root<Locality> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        //if regionId is null - dont restrict
        if (null == regionId) return criteriaBuilder.and();
        return criteriaBuilder.equal(root.get("region").get("id"), regionId);
    }
}
