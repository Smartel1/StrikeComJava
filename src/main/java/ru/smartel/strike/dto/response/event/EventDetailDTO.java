package ru.smartel.strike.dto.response.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.smartel.strike.dto.response.conflict.BriefConflictWithEventsDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.post.PostDetailDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.service.Locale;

import java.util.List;

public class EventDetailDTO extends PostDetailDTO {

    private double latitude;
    private double longitude;
    private long conflictId;
    private Long eventStatusId;
    private Long eventTypeId;
    private ConflictDetailDTO conflict;
    private ExtendedLocalityDTO locality;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BriefConflictWithEventsDTO> relatives;

    public static EventDetailDTO of(Event event, Locale locale) {
        EventDetailDTO instance = new EventDetailDTO();
        instance.setCommonFieldsOf(event, locale);
        instance.setLatitude(event.getLatitude());
        instance.setLongitude(event.getLongitude());
        instance.setConflictId(event.getConflict().getId());
        instance.setEventStatusId(null != event.getStatus() ? event.getStatus().getId() : null);
        instance.setEventTypeId(null != event.getType() ? event.getType().getId() : null);
        instance.setConflict(ConflictDetailDTO.of(event.getConflict(), locale));
        instance.setLocality(null != event.getLocality() ? ExtendedLocalityDTO.of(event.getLocality(), locale) : null);
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

    public ConflictDetailDTO getConflict() {
        return conflict;
    }

    public void setConflict(ConflictDetailDTO conflict) {
        this.conflict = conflict;
    }

    public ExtendedLocalityDTO getLocality() {
        return locality;
    }

    public void setLocality(ExtendedLocalityDTO locality) {
        this.locality = locality;
    }

    public List<BriefConflictWithEventsDTO> getRelatives() {
        return relatives;
    }

    public void setRelatives(List<BriefConflictWithEventsDTO> relatives) {
        this.relatives = relatives;
    }
}
