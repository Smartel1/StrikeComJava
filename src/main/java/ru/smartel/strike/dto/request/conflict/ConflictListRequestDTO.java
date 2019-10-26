package ru.smartel.strike.dto.request.conflict;

import ru.smartel.strike.dto.request.BaseListRequestDTO;

public class ConflictListRequestDTO extends BaseListRequestDTO {

    private ConflictFiltersDTO filters;

    public ConflictFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(ConflictFiltersDTO filters) {
        this.filters = filters;
    }
}
