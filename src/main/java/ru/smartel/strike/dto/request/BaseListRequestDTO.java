package ru.smartel.strike.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListRequestDTO {
    public static final int DEFAULT_PAGE_CAPACITY = 20;
    public static final int DEFAULT_PAGE = 1;

    private Integer perPage = DEFAULT_PAGE_CAPACITY;
    private Integer page = DEFAULT_PAGE;

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
