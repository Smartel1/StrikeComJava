package ru.smartel.strike.dto.request.news;

import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

public class NewsListRequestDTO extends BaseListRequestDTO {

    private Locale locale;
    private User user;
    private NewsFiltersDTO filters = new NewsFiltersDTO();

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

    public NewsFiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(NewsFiltersDTO filters) {
        this.filters = filters;
    }

}
