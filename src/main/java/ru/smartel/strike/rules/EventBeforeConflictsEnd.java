package ru.smartel.strike.rules;

import java.time.LocalDateTime;

public class EventBeforeConflictsEnd extends BusinessRule {
    private LocalDateTime eventDate;
    private LocalDateTime conflictDateTo;

    public EventBeforeConflictsEnd(LocalDateTime eventDate, LocalDateTime conflictDateTo) {
        this.eventDate = eventDate;
        this.conflictDateTo = conflictDateTo;
    }

    @Override
    public boolean passes() {
        // if conflict is not finished then rule passed
        if (conflictDateTo == null) {
            return true;
        }
        return conflictDateTo.compareTo(eventDate) >= 0;
    }

    @Override
    public String message() {
        return "Событие не должно произойти после завершения конфликта";
    }
}
