package ru.smartel.strike.repository.event;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.smartel.strike.entity.Event;

import java.util.List;
import java.util.Optional;


@Repository
public interface EventRepository extends CustomEventRepository, JpaSpecificationExecutor<Event>,
        JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = {"videos", "photos", "tags", "conflict"})
    @Override
    List<Event> findAllById(Iterable<Long> ids);

    List<Event> findAllByConflictId(long conflictId);

    long countByConflictId(long conflictId);

    Optional<Event> findFirstByConflictIdOrderByPostDateDesc(long conflictId);

    Optional<Event> findFirstByConflictIdAndLocalityNotNullOrderByPostDateDesc(long conflictId);
}
