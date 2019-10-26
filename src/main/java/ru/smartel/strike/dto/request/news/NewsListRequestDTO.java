package ru.smartel.strike.dto.request.news;

import ru.smartel.strike.dto.request.BaseListRequestDTO;

public class NewsListRequestDTO extends BaseListRequestDTO {

    private NewsFiltersDTO filters;

    public NewsFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(NewsFiltersDTO filters) {
        this.filters = filters;
    }

}
