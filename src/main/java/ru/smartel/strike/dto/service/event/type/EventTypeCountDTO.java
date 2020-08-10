package ru.smartel.strike.dto.service.event.type;

public class EventTypeCountDTO {
    private final long typeId;
    private final long eventsCount;
    private final int priority;

    public EventTypeCountDTO(long typeId, long eventsCount, int priority) {
        this.typeId = typeId;
        this.eventsCount = eventsCount;
        this.priority = priority;
    }

    public long getTypeId() {
        return typeId;
    }

    public long getEventsCount() {
        return eventsCount;
    }

    public int getPriority() {
        return priority;
    }
}
