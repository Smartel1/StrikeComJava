package ru.smartel.strike.dto.request.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.post.PostListRequestDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsListRequestDTO extends PostListRequestDTO {

    private NewsFiltersDTO filters;

    public NewsFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(NewsFiltersDTO filters) {
        this.filters = filters;
    }

}
