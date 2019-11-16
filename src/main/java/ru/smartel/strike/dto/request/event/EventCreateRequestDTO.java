package ru.smartel.strike.dto.request.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.security.token.UserPrincipal;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

/**
 * dto for creating event
 */
public class EventCreateRequestDTO extends PostRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private UserPrincipal user;
    private Optional<Long> conflictId;
    private Optional<Float> latitude;
    private Optional<Float> longitude;
    private Optional<Long> localityId;
    private Optional<Long> eventStatusId;
    private Optional<Long> eventTypeId;

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

    public Optional<Long> getConflictId() {
        return conflictId;
    }

    public void setConflictId(Optional<Long> conflictId) {
        this.conflictId = conflictId;
    }

    public Optional<Float> getLatitude() {
        return latitude;
    }

    public void setLatitude(Optional<Float> latitude) {
        this.latitude = latitude;
    }

    public Optional<Float> getLongitude() {
        return longitude;
    }

    public void setLongitude(Optional<Float> longitude) {
        this.longitude = longitude;
    }

    public Optional<Long> getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Optional<Long> localityId) {
        this.localityId = localityId;
    }

    public Optional<Long> getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Optional<Long> eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Optional<Long> getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Optional<Long> eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}
