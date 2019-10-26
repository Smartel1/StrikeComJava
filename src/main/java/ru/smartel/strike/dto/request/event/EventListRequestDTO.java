package ru.smartel.strike.dto.request.event;

import ru.smartel.strike.dto.request.BaseListRequestDTO;

public class EventListRequestDTO extends BaseListRequestDTO {

    private EventFiltersDTO filters;

    public EventFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(EventFiltersDTO filters) {
        this.filters = filters;
    }
}
