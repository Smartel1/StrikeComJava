package ru.smartel.strike.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ListWrapperDTO <T> {

    private List<T> data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Meta meta;

    public ListWrapperDTO(List<T> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static class Meta {
        private Long total;
        private Integer currentPage;
        private Integer perPage;
        private Long lastPage;

        public Meta(Long total, Integer currentPage, Integer perPage, Long lastPage) {
            this.total = total;
            this.currentPage = currentPage;
            this.perPage = perPage;
            this.lastPage = lastPage;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Long getLastPage() {
            return lastPage;
        }

        public void setLastPage(Long lastPage) {
            this.lastPage = lastPage;
        }
    }
}
