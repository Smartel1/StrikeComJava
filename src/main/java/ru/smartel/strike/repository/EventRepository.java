package ru.smartel.strike.repository;

import ru.smartel.strike.model.Event;

import java.util.List;

public interface EventRepository {
    Event find(int id);
    List<Event> get(int count);
    void store(Event event);
}
