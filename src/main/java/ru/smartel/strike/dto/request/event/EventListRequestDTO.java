package ru.smartel.strike.dto.request.event;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;

public class EventListRequestDTO extends BaseListRequestDTO {
    private Locale locale;
    private UserPrincipal user;
    private EventFiltersDTO filters = new EventFiltersDTO();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public UserPrincipal getUser() {
        return user;
    }

    public void setUser(UserPrincipal user) {
        this.user = user;
    }

    public EventFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(EventFiltersDTO filters) {
        this.filters = filters;
    }
}
