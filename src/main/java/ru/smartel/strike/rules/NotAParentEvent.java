package ru.smartel.strike.rules;

import ru.smartel.strike.repository.event.EventRepository;

public class NotAParentEvent extends BusinessRule {

    private int eventId;
    private EventRepository repository;

    public NotAParentEvent(int eventId, EventRepository repository) {
        this.eventId = eventId;
        this.repository = repository;
    }

    @Override
    public boolean passes() {
        return repository.isNotParentForAnyConflicts(eventId);
    }

    @Override
    public String message() {
        return "Это событие является родительским для конфликта";
    }
}
