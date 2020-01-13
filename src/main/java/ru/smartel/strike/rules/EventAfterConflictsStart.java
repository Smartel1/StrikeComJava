package ru.smartel.strike.rules;

import java.time.LocalDateTime;

public class EventAfterConflictsStart extends BusinessRule {
    private LocalDateTime eventDate;
    private LocalDateTime conflictDateFrom;

    public EventAfterConflictsStart(LocalDateTime eventDate, LocalDateTime conflictDateFrom) {
        this.eventDate = eventDate;
        this.conflictDateFrom = conflictDateFrom;
    }

    @Override
    public boolean passes() {
        // probably impossible situation
        if (conflictDateFrom == null) {
            return true;
        }
        return conflictDateFrom.compareTo(eventDate) <= 0;
    }

    @Override
    public String message() {
        return "Событие не должно произойти до начала конфликта";
    }
}
