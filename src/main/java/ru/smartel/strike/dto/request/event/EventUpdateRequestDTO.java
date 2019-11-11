package ru.smartel.strike.dto.request.event;


/**
 * dto for updating event
 */
public class EventUpdateRequestDTO extends EventCreateRequestDTO {
    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
