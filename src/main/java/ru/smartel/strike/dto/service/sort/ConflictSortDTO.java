package ru.smartel.strike.dto.service.sort;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.Conflict;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConflictSortDTO {

    private OrderField orderField;
    private OrderDirection orderDirection;

    private ConflictSortDTO() {
    }

    public static ConflictSortDTO of(BaseListRequestDTO.Sort sort) {
        ConflictSortDTO instance = new ConflictSortDTO();
        //default sorting
        if (sort == null) {
            instance.orderField = OrderField.CREATED_AT;
            instance.orderDirection = OrderDirection.DESC;
            return instance;
        }

        switch (sort.getField()) {
            case "createdAt":
                instance.orderField = OrderField.CREATED_AT;
                break;
            default:
                throw new IllegalStateException("Unknown sorting field for conflict: " + sort.getField());
        }
        switch (sort.getOrder()) {
            case "asc":
                instance.orderDirection = OrderDirection.ASC;
                break;
            case "desc":
                instance.orderDirection = OrderDirection.DESC;
                break;
            default:
                throw new IllegalStateException("Unknown sorting order for conflict: " + sort.getOrder());
        }
        return instance;
    }

    public Order toOrder(CriteriaBuilder cb, Root<Conflict> root) {
        return orderDirection.getOrder(cb, orderField.getPath(root));
    }

    public Comparator<Conflict> toComparator() {
        if (orderDirection.equals(OrderDirection.ASC)) {
            return orderField.getComparator();
        }
        return orderField.getComparator().reversed();
    }

    private enum OrderField {
        CREATED_AT((root) -> root.get("createdAt"), (c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()));

        private Function<Root<Conflict>, Path<Conflict>> pathMaker;
        private Comparator<Conflict> comparator;

        OrderField(Function<Root<Conflict>, Path<Conflict>> pathMaker, Comparator<Conflict> comparator) {
            this.pathMaker = pathMaker;
            this.comparator = comparator;
        }

        public Path<Conflict> getPath(Root<Conflict> root) {
            return pathMaker.apply(root);
        }

        public Comparator<Conflict> getComparator() {
            return comparator;
        }
    }

    private enum OrderDirection {
        ASC(CriteriaBuilder::asc),
        DESC(CriteriaBuilder::desc);

        private BiFunction<CriteriaBuilder, Path<Conflict>, Order> orderMaker;

        OrderDirection(BiFunction<CriteriaBuilder, Path<Conflict>, Order> orderMaker) {
            this.orderMaker = orderMaker;
        }

        public Order getOrder(CriteriaBuilder cb, Path<Conflict> path) {
            return orderMaker.apply(cb, path);
        }
    }
}
