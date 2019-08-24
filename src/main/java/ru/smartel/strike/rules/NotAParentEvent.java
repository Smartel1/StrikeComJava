package ru.smartel.strike.rules;

import ru.smartel.strike.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class NotAParentEvent extends BusinessRule {

    @PersistenceContext
    EntityManager entityManager;

    private Event event;

    public NotAParentEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean passes() {
        int relatedConflictsCount =  entityManager
                .createQuery("select c.id " +
                        "from ru.smartel.strike.model.Conflict c " +
                        "where c.parentEvent = :event")
                .setMaxResults(1)
                .setParameter("event", entityManager.getReference(Event.class, 21))
                .getResultList().size();

        return relatedConflictsCount == 0;
    }

    @Override
    public String message() {
        return "Это событие является родительским для конфликта";
    }
}
