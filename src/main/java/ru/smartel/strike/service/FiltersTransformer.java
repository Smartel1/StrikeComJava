package ru.smartel.strike.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FiltersTransformer {
    List<Specification> toSpecifications(ObjectNode jsonFilters, Integer userId);
}
