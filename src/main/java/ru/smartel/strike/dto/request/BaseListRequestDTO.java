package ru.smartel.strike.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiParam;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListRequestDTO {

    private static final int DEFAULT_PAGE_CAPACITY = 20;
    private static final int DEFAULT_PAGE = 1;

    @ApiParam(value = "Количество элементов на странице ответа (неменьше 1)")
    private int perPage = DEFAULT_PAGE_CAPACITY;
    @ApiParam(value = "Номер страницы ответа (неменьше 1)")
    private int page = DEFAULT_PAGE;
    private Sort sort;

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public static class Sort {
        @ApiParam(value = "Поле для сортировки (для конфликтов доступно: createdAt, для новостей и событий: createdAt, date, views)")
        private String field;
        @ApiParam(value = "Порядок сортировки", allowableValues = "desc, asc")
        private String order;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }
}
