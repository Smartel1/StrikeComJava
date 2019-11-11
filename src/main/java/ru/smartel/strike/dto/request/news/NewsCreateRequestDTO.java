package ru.smartel.strike.dto.request.news;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

/**
 * dto for creating news
 */
public class NewsCreateRequestDTO extends PostRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private User user;

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
}
