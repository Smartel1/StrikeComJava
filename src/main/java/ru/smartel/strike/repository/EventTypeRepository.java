package ru.smartel.strike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.reference.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
