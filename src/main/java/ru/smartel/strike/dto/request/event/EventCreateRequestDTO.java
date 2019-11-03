package ru.smartel.strike.dto.request.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.smartel.strike.dto.request.post.PostRequestDTO;
import ru.smartel.strike.entity.User;
import ru.smartel.strike.service.Locale;

import java.util.Optional;

/**
 * dto for creating event
 */
public class EventCreateRequestDTO extends PostRequestDTO {
    @JsonIgnore
    private Locale locale;
    @JsonIgnore
    private User user;
    private Optional<Integer> conflictId;
    private Optional<Float> latitude;
    private Optional<Float> longitude;
    private Optional<Integer> localityId;
    private Optional<Integer> eventStatusId;
    private Optional<Integer> eventTypeId;

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

    public Optional<Integer> getConflictId() {
        return conflictId;
    }

    public void setConflictId(Optional<Integer> conflictId) {
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

    public Optional<Integer> getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Optional<Integer> localityId) {
        this.localityId = localityId;
    }

    public Optional<Integer> getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Optional<Integer> eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Optional<Integer> getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Optional<Integer> eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}
