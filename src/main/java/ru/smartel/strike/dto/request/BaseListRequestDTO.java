package ru.smartel.strike.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListRequestDTO {

    public static final int DEFAULT_PAGE_CAPACITY = 20;
    public static final int DEFAULT_PAGE = 1;

    private Integer perPage = DEFAULT_PAGE_CAPACITY;
    private Integer page = DEFAULT_PAGE;

    /**
     * At the moment API users can send page/perPage by query params or in request body.
     * Request body has precedence if its values are non-default.
     * This method merges query parameters and dto parameters
     */
    public void mergeWith(int page, int perPage) {
        if (DEFAULT_PAGE == this.getPage()) setPage(page);
        if (DEFAULT_PAGE_CAPACITY == this.getPerPage()) setPerPage(perPage);
    }

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
