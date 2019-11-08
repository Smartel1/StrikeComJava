package ru.smartel.strike.dto.response.event;

import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.post.PostListDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.service.Locale;

public class EventListDTO extends PostListDTO {

    private double latitude;
    private double longitude;
    private int conflictId;
    private Integer eventStatusId;
    private Integer eventTypeId;
    private ConflictDetailDTO conflict;

    public static EventListDTO of(Event event, Locale locale) {
        EventListDTO instance = new EventListDTO();
        instance.setCommonFieldsOf(event, locale);
        instance.setLatitude(event.getLatitude());
        instance.setLongitude(event.getLongitude());
        instance.setConflictId(event.getConflict().getId());
        instance.setEventStatusId(null != event.getStatus() ? event.getStatus().getId() : null);
        instance.setEventTypeId(null != event.getType() ? event.getType().getId() : null);
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

    public int getConflictId() {
        return conflictId;
    }

    public void setConflictId(int conflictId) {
        this.conflictId = conflictId;
    }

    public Integer getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Integer eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public ConflictDetailDTO getConflict() {
        return conflict;
    }

    public void setConflict(ConflictDetailDTO conflict) {
        this.conflict = conflict;
    }
}
