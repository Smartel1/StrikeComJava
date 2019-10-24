package ru.smartel.strike.dto.request.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.BaseListRequestDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostListRequestDTO  extends BaseListRequestDTO {

    private PostFiltersDTO filters;

    public PostFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(PostFiltersDTO filters) {
        this.filters = filters;
    }
}
