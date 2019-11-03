package ru.smartel.strike.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListRequestDTO {

    private static final int DEFAULT_PAGE_CAPACITY = 20;
    private static final int DEFAULT_PAGE = 1;

    private int perPage = DEFAULT_PAGE_CAPACITY;
    private int page = DEFAULT_PAGE;

    /**
     * At the moment API users can send page/perPage by query params or in request body.
     * Request body has precedence if its values are non-default.
     * This method merges query parameters and dto parameters
     */
    public void mergeWith(int page, int perPage) {
        if (DEFAULT_PAGE == this.getPage()) setPage(page);
        if (DEFAULT_PAGE_CAPACITY == this.getPerPage()) setPerPage(perPage);
    }

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
