package ru.smartel.strike.dto.request.event;


/**
 * dto for updating event
 */
public class EventUpdateRequestDTO extends EventCreateRequestDTO {
    private int eventId;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
