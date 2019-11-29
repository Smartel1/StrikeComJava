package ru.smartel.strike.dto.response.event;

import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.post.PostListDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.Optional;

public class EventListDTO extends PostListDTO {

    private double latitude;
    private double longitude;
    private long conflictId;
    private Long eventStatusId;
    private Long eventTypeId;
    private Long createdAt;
    private ConflictDetailDTO conflict;

    public static EventListDTO of(Event event, Locale locale) {
        EventListDTO instance = new EventListDTO();
        instance.setCommonFieldsOf(event, locale);
        instance.setLatitude(event.getLatitude());
        instance.setLongitude(event.getLongitude());
        instance.setConflictId(event.getConflict().getId());
        instance.setEventStatusId(null != event.getStatus() ? event.getStatus().getId() : null);
        instance.setEventTypeId(null != event.getType() ? event.getType().getId() : null);
        instance.setCreatedAt(Optional.ofNullable(event.getCreatedAt())
                .map(d -> d.toEpochSecond(ZoneOffset.UTC))
                .orElse(null));
        instance.setConflict(ConflictDetailDTO.of(event.getConflict(), locale));
        return instance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getConflictId() {
        return conflictId;
    }

    public void setConflictId(long conflictId) {
        this.conflictId = conflictId;
    }

    public Long getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Long eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public ConflictDetailDTO getConflict() {
        return conflict;
    }

    public void setConflict(ConflictDetailDTO conflict) {
        this.conflict = conflict;
    }
}
