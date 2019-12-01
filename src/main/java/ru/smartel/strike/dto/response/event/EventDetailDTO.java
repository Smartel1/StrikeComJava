package ru.smartel.strike.dto.response.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.smartel.strike.dto.response.conflict.BriefConflictWithEventsDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.post.PostDetailDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.entity.Event;
import ru.smartel.strike.service.Locale;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

public class EventDetailDTO extends PostDetailDTO {

    private float latitude;
    private float longitude;
    private long conflictId;
    private Long eventStatusId;
    private Long eventTypeId;
    private ConflictDetailDTO conflict;
    private ExtendedLocalityDTO locality;
    private Long createdAt;
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
        instance.setCreatedAt(Optional.ofNullable(event.getCreatedAt())
                .map(d -> d.toEpochSecond(ZoneOffset.UTC))
                .orElse(null));
        return instance;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public List<BriefConflictWithEventsDTO> getRelatives() {
        return relatives;
    }

    public void setRelatives(List<BriefConflictWithEventsDTO> relatives) {
        this.relatives = relatives;
    }
}
