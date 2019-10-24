package ru.smartel.strike.dto.request.event;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.smartel.strike.dto.request.post.PostRequestDTO;

import java.util.Optional;

/**
 * dto for creating/updating event requests
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequestDTO extends PostRequestDTO {
    private Optional<Integer> conflictId;
    private Optional<Float> latitude;
    private Optional<Float> longitude;
    private Optional<Integer> localityId;
    private Optional<Integer> eventStatusId;
    private Optional<Integer> eventTypeId;

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
