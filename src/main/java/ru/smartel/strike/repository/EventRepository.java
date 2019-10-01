package ru.smartel.strike.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer>, CustomEventRepository {
}
