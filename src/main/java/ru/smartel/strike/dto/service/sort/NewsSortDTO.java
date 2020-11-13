package ru.smartel.strike.dto.service.sort;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NewsSortDTO {

    private OrderField orderField;
    private OrderDirection orderDirection;

    private NewsSortDTO() {
    }

    public static NewsSortDTO of(BaseListRequestDTO.Sort sort) {
        NewsSortDTO instance = new NewsSortDTO();
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
                throw new IllegalStateException("Unknown sorting field for news: " + sort.getField());
        }
        switch (sort.getOrder()) {
            case "asc":
                instance.orderDirection = OrderDirection.ASC;
                break;
            case "desc":
                instance.orderDirection = OrderDirection.DESC;
                break;
            default:
                throw new IllegalStateException("Unknown sorting order for news: " + sort.getOrder());
        }
        return instance;
    }

    public Order toOrder(CriteriaBuilder cb, Root<News> root) {
        return orderDirection.getOrder(cb, orderField.getPath(root));
    }

    public Comparator<News> toComparator() {
        if (orderDirection.equals(OrderDirection.ASC)) {
            return orderField.getComparator();
        }
        return orderField.getComparator().reversed();
    }

    private enum OrderField {
        CREATED_AT((root) -> root.get("createdAt"), (c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt())),
        DATE((root) -> root.get("post").get("date"), (c1, c2) -> c1.getDate().compareTo(c2.getDate())),
        VIEWS((root) -> root.get("post").get("views"), (c1, c2) -> c1.getViews().compareTo(c2.getViews()));

        private final Function<Root<News>, Path<News>> pathMaker;
        private final Comparator<News> comparator;

        OrderField(Function<Root<News>, Path<News>> pathMaker, Comparator<News> comparator) {
            this.pathMaker = pathMaker;
            this.comparator = comparator;
        }

        public Path<News> getPath(Root<News> root) {
            return pathMaker.apply(root);
        }

        public Comparator<News> getComparator() {
            return comparator;
        }
    }

    private enum OrderDirection {
        ASC(CriteriaBuilder::asc),
        DESC(CriteriaBuilder::desc);

        private final BiFunction<CriteriaBuilder, Path<News>, Order> orderMaker;

        OrderDirection(BiFunction<CriteriaBuilder, Path<News>, Order> orderMaker) {
            this.orderMaker = orderMaker;
        }

        public Order getOrder(CriteriaBuilder cb, Path<News> path) {
            return orderMaker.apply(cb, path);
        }
    }
}
