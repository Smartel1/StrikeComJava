package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

import java.util.List;


@Repository
public interface EventRepository extends
        CustomEventRepository,
        JpaSpecificationExecutor<Event>,
        JpaRepository<Event, Integer>
{
    @EntityGraph(attributePaths = {"videos", "photos", "tags", "conflict"})
    @Override
    List<Event> findAllById(Iterable<Integer> ids);
}
