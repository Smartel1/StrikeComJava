package ru.smartel.strike.repository;

import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

@Repository
public interface CustomEventRepository {
    Event findOrThrow(int id);
}
