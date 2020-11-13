package ru.smartel.strike.dto.service.sort;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EventSortDTO {

    private OrderField orderField;
    private OrderDirection orderDirection;

    private EventSortDTO() {
    }

    public static EventSortDTO of(BaseListRequestDTO.Sort sort) {
        EventSortDTO instance = new EventSortDTO();
        //default sorting
        if (sort == null) {
            instance.orderField = OrderField.DATE;
            instance.orderDirection = OrderDirection.DESC;
            return instance;
        }

        switch (sort.getField()) {
            case "createdAt":
                instance.orderField = OrderField.CREATED_AT;
                break;
            case "date":
                instance.orderField = OrderField.DATE;
                break;
            case "views":
                instance.orderField = OrderField.VIEWS;
                break;
            default:
                throw new IllegalStateException("Unknown sorting field for event: " + sort.getField());
        }
        switch (sort.getOrder()) {
            case "asc":
                instance.orderDirection = OrderDirection.ASC;
                break;
            case "desc":
                instance.orderDirection = OrderDirection.DESC;
                break;
            default:
                throw new IllegalStateException("Unknown sorting order for event: " + sort.getOrder());
        }
        return instance;
    }

    public Order toOrder(CriteriaBuilder cb, Root<Event> root) {
        return orderDirection.getOrder(cb, orderField.getPath(root));
    }

    public Comparator<Event> toComparator() {
        if (orderDirection.equals(OrderDirection.ASC)) {
            return orderField.getComparator();
        }
        return orderField.getComparator().reversed();
    }

    private enum OrderField {
        CREATED_AT((root) -> root.get("createdAt"), (c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt())),
        DATE((root) -> root.get("post").get("date"), (c1, c2) -> c1.getDate().compareTo(c2.getDate())),
        VIEWS((root) -> root.get("post").get("views"), (c1, c2) -> c1.getViews().compareTo(c2.getViews()));

        private final Function<Root<Event>, Path<Event>> pathMaker;
        private final Comparator<Event> comparator;

        OrderField(Function<Root<Event>, Path<Event>> pathMaker, Comparator<Event> comparator) {
            this.pathMaker = pathMaker;
            this.comparator = comparator;
        }

        public Path<Event> getPath(Root<Event> root) {
            return pathMaker.apply(root);
        }

        public Comparator<Event> getComparator() {
            return comparator;
        }
    }

    private enum OrderDirection {
        ASC(CriteriaBuilder::asc),
        DESC(CriteriaBuilder::desc);

        private final BiFunction<CriteriaBuilder, Path<Event>, Order> orderMaker;

        OrderDirection(BiFunction<CriteriaBuilder, Path<Event>, Order> orderMaker) {
            this.orderMaker = orderMaker;
        }

        public Order getOrder(CriteriaBuilder cb, Path<Event> path) {
            return orderMaker.apply(cb, path);
        }
    }
}
