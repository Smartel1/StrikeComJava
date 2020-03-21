package ru.smartel.strike.dto.request.conflict;

import io.swagger.annotations.ApiParam;
import ru.smartel.strike.dto.request.BaseListRequestDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;

public class ConflictListRequestDTO extends BaseListRequestDTO {
    private Locale locale;
    private UserPrincipal user;
    @ApiParam(value = "Вывести краткую информацию о конфликтах")
    private boolean brief;
    private ConflictFiltersDTO filters = new ConflictFiltersDTO();

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
