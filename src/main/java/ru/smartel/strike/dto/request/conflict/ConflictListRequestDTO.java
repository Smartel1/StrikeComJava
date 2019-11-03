package ru.smartel.strike.dto.request.conflict;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

public class ConflictListRequestDTO extends BaseListRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private User user;
    private boolean brief;
    private ConflictFiltersDTO filters = new ConflictFiltersDTO();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isBrief() {
        return brief;
    }

    public void setBrief(boolean brief) {
        this.brief = brief;
    }

    public ConflictFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(ConflictFiltersDTO filters) {
        this.filters = filters;
    }
}
