package ru.smartel.strike.repository;

import ru.smartel.strike.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Event findOrThrow(int id) {
        Event event = entityManager.find(Event.class, id);
        if (null == event) throw new EntityNotFoundException("Событие не найдено");
        return event;
    }
}
