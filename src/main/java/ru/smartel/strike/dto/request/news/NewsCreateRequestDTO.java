package ru.smartel.strike.dto.request.news;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;

/**
 * dto for creating news
 */
public class NewsCreateRequestDTO extends PostRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private UserPrincipal user;

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
}
