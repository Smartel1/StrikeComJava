package ru.smartel.strike.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListRequestDTO {

    private static final int DEFAULT_PAGE_CAPACITY = 20;
    private static final int DEFAULT_PAGE = 1;

    private int perPage = DEFAULT_PAGE_CAPACITY;
    private int page = DEFAULT_PAGE;

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
}
