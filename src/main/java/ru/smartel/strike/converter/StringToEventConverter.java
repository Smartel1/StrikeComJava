package ru.smartel.strike.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.smartel.strike.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Component
public class StringToEventConverter implements Converter<String, Event> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Event convert(String id) throws EntityNotFoundException {
        return entityManager.find(Event.class, Integer.parseInt(id));
    }
}
