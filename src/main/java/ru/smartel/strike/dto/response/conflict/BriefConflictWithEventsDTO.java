package ru.smartel.strike.dto.response.conflict;

import ru.smartel.strike.dto.response.event.BriefEventDTO;

import java.util.List;

public class BriefConflictWithEventsDTO {
    private long id;
    private Long parentEventId;
    private Long parentConflictId;
    private List<BriefEventDTO> events;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Long parentEventId) {
        this.parentEventId = parentEventId;
    }

    public Long getParentConflictId() {
        return parentConflictId;
    }

    public void setParentConflictId(Long parentConflictId) {
        this.parentConflictId = parentConflictId;
    }

    public List<BriefEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<BriefEventDTO> events) {
        this.events = events;
    }
}
