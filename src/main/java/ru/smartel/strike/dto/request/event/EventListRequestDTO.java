package ru.smartel.strike.dto.request.event;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

public class EventListRequestDTO extends BaseListRequestDTO {

    private Locale locale;
    private User user;
    private EventFiltersDTO filters = new EventFiltersDTO();

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

    public EventFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(EventFiltersDTO filters) {
        this.filters = filters;
    }
}
