package ru.smartel.strike.dto.request.event;


import ru.smartel.strike.service.Locale;

/**
 * dto for detail view request
 */
public class EventShowDetailRequestDTO {
    private Locale locale;
    private long id;
    private boolean withRelatives;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWithRelatives() {
        return withRelatives;
    }

    public void setWithRelatives(boolean withRelatives) {
        this.withRelatives = withRelatives;
    }
}
