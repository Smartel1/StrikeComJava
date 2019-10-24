package ru.smartel.strike.dto.request.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.post.PostListRequestDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventListRequestDTO extends PostListRequestDTO {

    private EventFiltersDTO filters;

    public EventFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(EventFiltersDTO filters) {
        this.filters = filters;
    }
}
