package ru.smartel.strike.dto.request.event;


/**
 * dto for updating event
 */
public class EventUpdateRequestDTO extends EventCreateRequestDTO {
    private Integer eventId;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
